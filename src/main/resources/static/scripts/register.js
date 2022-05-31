//This function redirects to register controller and register new user to system
function register() {
    location.href = '/register/' + document.getElementById("id").value + '/' + document.getElementById("pwd").value
        + '/' + document.getElementById("name").value + '/' + document.getElementById("surname").value + '/' + document.getElementById("manager").checked;
}