var usernameTextbox = document.querySelector("#username")

usernameTextbox.addEventListener('blur', () => {
    var user = {
    'username': usernameTextbox.value,

    }
    fetch('/users/exists', {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body:JSON.stringify(user)
    })
    .then( (responseEntity) => responseEntity.json() )
    .then( (data) => {
    if (data === true) {
        //this user exists already!
        alert("Whoop, There it is!")
        //usernameTextbox.select() and .focus() but caused it to keep popping up endlessly
    }

})
})