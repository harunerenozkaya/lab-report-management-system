//This function redirects to addReport controller and add new report to system
function addReport() {
    location.href = '/addReport/' + document.getElementById("id").value + '/' + document.getElementById("patient-name").value
        + '/' + document.getElementById("patient-surname").value + '/' + document.getElementById("patient-TC").value + '/' + document.getElementById("diagnosis-title").value
        + '/' + document.getElementById("diagnosis-detail").value;
}