var submitBtn = document.querySelector("#submitBtn")
var users = []

submitBtn.addEventListener('click', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")

    if (username.value == '' || password.value == '') {
        alert("Please enter a valid username and password")
    } else {
        console.log("Input looks legit, proceed with caution")
        var user = {
            "username" : username.value,
            "password" : password.value
        }

        users .push(user);
    }
})

username.addEventListener('blur', () => {
    var username = document.querySelector("#username")
    var password = document.querySelector("#password")

    var user = {
        "username": username.value,
        "password": password.value
    }
    fetch(`http://localhost:8080/users/exists`, {
    method: "POST",
    headers:  {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
})
    .then((response) => response.json())
    .then((data) => {
        console.log(data)
    })
})
