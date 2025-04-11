document.addEventListener("DOMContentLoaded", function () {
    const token = sessionStorage.getItem("authToken");
    const username = sessionStorage.getItem("username");


    document.getElementById("welcomeMessage").innerText = `Welcome, ${username}!`;

    document.getElementById("logoutBtn").addEventListener("click", () => {
        sessionStorage.clear();
        window.location.href = "login.html";
    });
});