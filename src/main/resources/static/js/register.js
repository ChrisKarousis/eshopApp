document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("registerForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        const formData = new FormData(this);
        const user = {};
        formData.forEach((value, key) => {
            user[key] = value;
        });

        const message = document.getElementById("message");

        try {
            const response = await fetch("http://localhost:8080/eshop/users/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            });

            if (response.ok) {
                const registeredUser = await response.json();
                message.innerText = `Registration successful! Welcome, ${registeredUser.firstName || registeredUser.username}.`;
                message.style.color = "green";
            } else {
                message.innerText = "Registration failed. Please try again.";
                message.style.color = "red";
            }
        } catch (error) {
            console.error("Error:", error);
            message.innerText = "An error occurred. Please try again.";
            message.style.color = "red";
        }
    });
});
