document.addEventListener("DOMContentLoaded", function () {

    fetch('/eshop/users/username')
        .then(response => response.json())
        .then(user => {
            // Store the username in sessionStorage
            const username=user.username;
            const userId=user.id;
            sessionStorage.setItem("username", username);
            sessionStorage.setItem("userId", userId);
            console.log("Username " + username);

            // Display the username in the welcome message
            document.getElementById("welcomeMessage").innerText = `Welcome, ${username}!`;
        })
        .catch(error => {
            console.error('Error fetching username:', error);
        });

    document.getElementById("logoutBtn").addEventListener("click", () => {
        sessionStorage.clear();
        window.location.href = "/login";
    });
});