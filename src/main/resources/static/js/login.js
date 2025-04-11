document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const message = document.getElementById("message");

        try {
            const response = await fetch("http://localhost:8080/eshop/users/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const user = await response.json();
                sessionStorage.setItem("username", user.username);
                window.location.href = "home.html";
            } else {
                message.innerText = "Invalid credentials!";
                message.style.color = "red";
            }
        } catch (err) {
            console.error("Error:", err);
            message.innerText = "Something went wrong.";
            message.style.color = "red";
        }
    });
});