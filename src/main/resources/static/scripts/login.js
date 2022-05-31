//This function redirects to login controller and control whether login inputs are correct
function login() {
    alert("geldi");
    location.href = 'login/' + document.getElementById("id").value + '/' + document.getElementById("pwd").value;
}
