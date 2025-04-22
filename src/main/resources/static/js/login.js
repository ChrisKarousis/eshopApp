document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("loginForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;
        const message = document.getElementById("message");
        fetch(`eshop/users/username/${login}`, {  // Ensure your backend uses this URL pattern
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('User not found or login failed');
                }
                return response.json();  // Parse the response to JSON
            })
            .then(data => {
                // Assuming your backend returns a user object with an `id` field
                const userId = data.id;
                console.log(userId);

                // Store the userId in sessionStorage
                sessionStorage.setItem("userId", userId);
            })
            .catch(err => {
                // Handle errors, e.g., user not found or network issues
                console.error('Error fetching user:', err);
                message.textContent = 'Login failed. Please try again.';
            });

        const payload = { login, password };

        try {
            const response = await fetch("http://localhost:8080/eshop/users/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
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