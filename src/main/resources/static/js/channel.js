// Constants
const API_ENDPOINTS = {
    CHANNELS: '/channels',
    CREATE_CHANNEL: '/channels/create',
    RENAME_CHANNEL: (id) => `/channels/${id}/rename`,
    SEND_MESSAGE: (id) => `/channels/${id}`
};

const ERROR_MESSAGES = {
    NETWORK_ERROR: 'Network error occurred. Please try again.',
    SERVER_ERROR: 'Server error occurred. Please try again.',
    INVALID_INPUT: 'Please provide valid input.',
    CHANNEL_EXISTS: 'A channel with this name already exists.'
};

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
                // Add CSRF token if needed
                // 'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
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

function editChannelName(event, channelId) {
    event.stopPropagation();
    const span = event.target;
    const currentName = span.textContent;
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
    
    span.parentNode.replaceChild(input, span);
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
    // Clear form
    document.getElementById('newChannelName').value = '';
}

// Form handling
document.addEventListener('DOMContentLoaded', function() {
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
    if (messageForm) {
        messageForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const submitButton = this.querySelector('button[type="submit"]');
            const messageInput = document.getElementById('message-input');
            const message = messageInput.value.trim();
            const channelId = this.dataset.channelId;
            
            if (!message) {
                return;
            }

            showLoading(submitButton);
            
            try {
                const data = await fetchWithErrorHandling(API_ENDPOINTS.SEND_MESSAGE(channelId), {
                    method: 'POST',
                    body: JSON.stringify({
                        text: message,
                        momentInTime: new Date().toISOString(),
                        sender: window.username
                    })
                });

                if (data) {
                    const messagesContainer = document.getElementById('messages');
                    const now = new Date();
                    const timeString = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                    
                    const messageHTML = `
                        <div class="message">
                            <div class="message-avatar">
                                <i class="fas fa-user"></i>
                            </div>
                            <div class="message-content">
                                <div class="message-header">
                                    <span class="message-author">${window.username}</span>
                                    <span class="message-time">Today at ${timeString}</span>
                                </div>
                                <div class="message-text">
                                    ${message}
                                </div>
                            </div>
                        </div>
                    `;
                    
                    messagesContainer.insertAdjacentHTML('beforeend', messageHTML);
                    messageInput.value = '';
                    messagesContainer.scrollTop = messagesContainer.scrollHeight;
                }
            } catch (error) {
                showError(ERROR_MESSAGES.NETWORK_ERROR);
            } finally {
                hideLoading(submitButton);
            }
        });
    }
}); 