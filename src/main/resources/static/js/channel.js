// Constants
const API_ENDPOINTS = {
    CHANNELS: '/channels',
    CREATE_CHANNEL: '/channels/create',
    RENAME_CHANNEL: (id) => `/channels/${id}/rename`,
    SEND_MESSAGE: (id) => `/channels/${id}/messages`,
    DELETE_CHANNEL: (id) => `/channels/${id}`
};

const ERROR_MESSAGES = {
    NETWORK_ERROR: 'Network error occurred. Please try again.',
    SERVER_ERROR: 'Server error occurred. Please try again.',
    INVALID_INPUT: 'Please provide valid input.',
    CHANNEL_EXISTS: 'A channel with this name already exists.'
};

// Theme Management
function initTheme() {
    const theme = localStorage.getItem('theme') || 'dark';
    document.documentElement.setAttribute('data-theme', theme);
    updateThemeIcon(theme);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    updateThemeIcon(newTheme);
}

function updateThemeIcon(theme) {
    const icon = document.querySelector('#theme-toggle i');
    icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
}

// Utility functions
function showError(message) {
    // TODO: Implement proper error notification system
    console.error(message);
    alert(message);
}

function showLoading(element) {
    element.disabled = true;
    element.dataset.originalText = element.textContent;
    element.textContent = 'Loading...';
}

function hideLoading(element) {
    element.disabled = false;
    element.textContent = element.dataset.originalText;
}

async function fetchWithErrorHandling(url, options) {
    try {
        const response = await fetch(url, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
        throw error;
    }
}

// Channel management
function joinChannel(channelId) {
    window.location.href = `/channels/${channelId}`;
}

async function deleteChannel(channelId) {
    if (!confirm('Are you sure you want to delete this channel?')) {
        return;
    }

    try {
        const response = await fetch(API_ENDPOINTS.DELETE_CHANNEL(channelId), {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const data = await response.json();

        if (data.success) {
            window.location.href = '/channels';
        } else {
            showError(data.error || ERROR_MESSAGES.SERVER_ERROR);
        }
    } catch (error) {
        showError(ERROR_MESSAGES.NETWORK_ERROR);
        console.error("Error deleting channel:", error);
    }
}

function editChannelName(event, channelId) {
    event.stopPropagation();
    const channelTitle = document.querySelector('.channel-title h1');
    const currentName = channelTitle.textContent;
    const input = document.createElement('input');
    input.type = 'text';
    input.value = currentName;
    input.className = 'channel-name-edit';
    
    input.onblur = function() {
        updateChannelName(channelId, this.value);
    };
    
    input.onkeypress = function(e) {
        if (e.key === 'Enter') {
            this.blur();
        }
    };
    
    channelTitle.parentNode.replaceChild(input, channelTitle);
    input.focus();
}

async function updateChannelName(channelId, newName) {
    if (!newName.trim()) {
        showError(ERROR_MESSAGES.INVALID_INPUT);
        return;
    }

    try {
        const data = await fetchWithErrorHandling(API_ENDPOINTS.RENAME_CHANNEL(channelId), {
            method: 'POST',
            body: JSON.stringify({ name: newName.trim() })
        });

        if (data.success) {
            window.location.reload();
        } else {
            showError(data.error || ERROR_MESSAGES.SERVER_ERROR);
        }
    } catch (error) {
        showError(ERROR_MESSAGES.NETWORK_ERROR);
    }
}

// Modal management
function showNewChannelModal() {
    document.getElementById('newChannelModal').style.display = 'flex';
}

function closeNewChannelModal() {
    document.getElementById('newChannelModal').style.display = 'none';
    document.getElementById('newChannelName').value = '';
}

// Add these constants at the top
const POLL_INTERVAL = 500; // 500ms polling interval
let lastMessageId = 0;

// Add this function for message polling
async function pollNewMessages() {
    if (!window.currentChannelId) return;
    
    try {
        const response = await fetch(`/channels/${window.currentChannelId}/messages?lastId=${lastMessageId}`);
        const data = await response.json();
        
        if (data.messages && data.messages.length > 0) {
            data.messages.forEach(message => {
                if (message.messageId > lastMessageId) {
                    lastMessageId = message.messageId;
                    appendMessage(message, false);
                    
                    // Show notification if message is from another user
                    if (message.sender && message.sender.username !== window.username) {
                        showNotification(message);
                    }
                }
            });
        }
    } catch (error) {
        console.error('Error polling messages:', error);
    }
}

// Add notification function
function showNotification(message) {
    if (!("Notification" in window)) return;
    
    if (Notification.permission === "granted") {
        new Notification("New Message", {
            body: `${message.sender.username}: ${message.text}`,
            icon: "/images/logo.png"
        });
    } else if (Notification.permission !== "denied") {
        Notification.requestPermission();
    }
}

// Message handling
function getInitials(username) {
    return username
        .split(' ')
        .map(word => word[0])
        .join('')
        .toUpperCase()
        .slice(0, 2);
}

function appendMessage(message) {
    const messagesContainer = document.getElementById('messages');
    const isOwnMessage = message.sender.username === window.username;
    
    // Check if we should group with previous message
    const lastMessage = messagesContainer.lastElementChild;
    const shouldGroup = lastMessage && 
                       lastMessage.classList.contains('message') &&
                       lastMessage.dataset.sender === message.sender.username &&
                       (new Date(message.momentInTime) - new Date(lastMessage.dataset.timestamp)) < 300000; // 5 minutes
    
    const messageDiv = document.createElement('div');
    messageDiv.className = `message ${isOwnMessage ? 'own-message' : ''} ${shouldGroup ? 'grouped' : ''}`;
    messageDiv.dataset.sender = message.sender.username;
    messageDiv.dataset.timestamp = message.momentInTime;
    
    // Parse the ISO string and convert to local time
    const time = new Date(message.momentInTime);
    const formattedTime = time.toLocaleTimeString('en-US', { 
        hour: 'numeric',
        minute: '2-digit',
        hour12: true 
    }).replace(/^0/, ''); // Remove leading zero from hour
    
    messageDiv.innerHTML = `
        ${!shouldGroup ? `<div class="message-avatar">
            ${getInitials(message.sender.username)}
        </div>` : ''}
        <div class="message-content">
            ${!shouldGroup ? `<div class="message-header">
                <span class="message-author">${message.sender.username}</span>
                <span class="message-time">${formattedTime}</span>
            </div>` : ''}
            <div class="message-text">${message.text}</div>
        </div>
    `;
    
    messagesContainer.appendChild(messageDiv);
}

// Update message form handler
document.addEventListener('DOMContentLoaded', function() {
    // Initialize theme
    initTheme();
     
    

    // Theme toggle
    const themeToggle = document.getElementById('theme-toggle');
    if (themeToggle) {
        themeToggle.addEventListener('click', toggleTheme);
    }

    // New channel form
    const newChannelForm = document.getElementById('newChannelForm');
    if (newChannelForm) {
        newChannelForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const submitButton = this.querySelector('button[type="submit"]');
            const channelName = document.getElementById('newChannelName').value.trim();
            
            if (!channelName) {
                showError(ERROR_MESSAGES.INVALID_INPUT);
                return;
            }

            showLoading(submitButton);
            
            try {
                const data = await fetchWithErrorHandling(API_ENDPOINTS.CREATE_CHANNEL, {
                    method: 'POST',
                    body: JSON.stringify({ name: channelName })
                });

                if (data.success) {
                    closeNewChannelModal();
                    window.location.href = `/channels/${data.channelId}`;
                } else {
                    showError(data.error || ERROR_MESSAGES.SERVER_ERROR);
                }
            } catch (error) {
                showError(ERROR_MESSAGES.NETWORK_ERROR);
            } finally {
                hideLoading(submitButton);
            }
        });
    }

    // Message form
    const messageForm = document.getElementById('message-form');
    if (messageForm && messageInput) {
        messageForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const channelId = this.dataset.channelId;
            const text = messageInput.value.trim();
            
            if (!text) return;
            
            try {
                const now = new Date();
                // Format date to match Java's OffsetDateTime format (yyyy-MM-ddTHH:mm:ssXXX)
                const formattedDate = now.toISOString(); // This includes the timezone offset
                
                const response = await fetch(`/channels/${channelId}/messages`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        text: text,
                        momentInTime: formattedDate,
                        channelId: channelId
                    })
                });

                if (response.ok) {
                    messageInput.value = ''; // Clear the input
                    messageInput.focus(); // Keep focus on input
                }
            } catch (error) {
                console.error('Error sending message:', error);
            }
        });
    }

    // Fetch messages periodically
    if (window.currentChannelId) {
        setInterval(async () => {
            try {
                const response = await fetch(`/channels/${window.currentChannelId}/get-messages`);
                if (response.ok) {
                    const channels = await response.json();
                    const currentChannel = channels.find(c => c.channelId === window.currentChannelId);
                    if (currentChannel) {
                        const messagesContainer = document.getElementById('messages');
                        messagesContainer.innerHTML = '';
                        currentChannel.chats.forEach(chat => appendMessage(chat));
                    }
                }
            } catch (error) {
                console.error('Error fetching messages:', error);
            }
        }, 500); // Fetch every 500 milliseconds
    }
}); 