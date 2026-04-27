window.onload = function () {

    // 🔐 لو فيه token ادخّل مباشرة
    const token = localStorage.getItem("token");

    if (token) {
        window.location.href = "../dashboard.html";
        return;
    }
};

document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const username = document.querySelector('input[type="text"]').value;
    const password = document.querySelector('input[type="password"]').value;

    const response = await fetch("http://localhost:8080/api/auth/login", {
        
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })

    });

    if (!response.ok) {
        alert("Login failed");
        
    }else {
        const data = await response.text();

        // 💾 خزن التوكن
        localStorage.setItem("token", data);


        // 🚀 روح للـ dashboard
        window.location.href = "../dashboard.html";
    }


});