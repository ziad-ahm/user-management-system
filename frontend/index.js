const BASE_URL = "http://localhost:8080/api/users";

/* =========================
   TOKEN HELPERS
========================= */
function getToken() {
    return localStorage.getItem("token");
}

function isTokenExpired(token) {
    if (!token) return true;

    try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        const now = Math.floor(Date.now() / 1000);
        return payload.exp < now;
    } catch (e) {
        return true;
    }
}

function authGuard() {
    const token = getToken();

    if (!token || isTokenExpired(token)) {
        localStorage.removeItem("token");
        window.location.href = "/login Page/login.html";
    }
}

/* =========================
   AUTO INIT
========================= */
window.addEventListener("load", function () {
    authGuard();
    getUsers();
});

/* =========================
   LOGOUT
========================= */
document.querySelector(".logout").addEventListener("click", () => {
    localStorage.removeItem("token");
    window.location.href = "/login Page/login.html";
});

/* =========================
   USERS
========================= */
function getUsers() {

    fetch(BASE_URL, {
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Unauthorized");
        return res.json();
    })
    .then(data => renderUsers(data))
    .catch(() => authGuard());
}

/* =========================
   RENDER USERS + STATS
========================= */
function renderUsers(data) {

    const table = document.getElementById("usersTable");

    let rows = "";
    let totalSalary = 0;
    let admins = 0;
    let accountants = 0;

    data.forEach(user => {

        rows += `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.jobTitle}</td>
                <td>${user.salary}</td>
                <td>${user.role}</td>
                <td>
                    <button onclick="openEditUser(${user.id})">Edit</button>
                    <button onclick="deleteUser(${user.id})">Delete</button>
                </td>
            </tr>
        `;

        totalSalary += Number(user.salary || 0);

        if (user.role === "ADMIN") admins++;
        if (user.role === "ACCOUNTANT") accountants++;
    });

    table.innerHTML = rows;

    document.getElementById("totalUsers").innerText = data.length;
    document.getElementById("admins").innerText = admins;
    document.getElementById("accountants").innerText = accountants;
    document.getElementById("salary").innerText = totalSalary;
}

/* =========================
   CREATE / UPDATE USER
========================= */
let editMode = false;
let editUserId = null;

function createUser() {

    const user = {
        username: username.value,
        password: password.value,
        email: email.value,
        firstName: firstName.value,
        lastName: lastName.value,
        jobTitle: jobTitle.value,
        salary: Number(salary.value),
        role: role.value
    };

    const url = editMode ? `${BASE_URL}/${editUserId}` : BASE_URL;
    const method = editMode ? "PUT" : "POST";

    fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + getToken()
        },
        body: JSON.stringify(user)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        return res.json();
    })
    .then(() => {
        closeModal();
        clearForm();
        getUsers();

        editMode = false;
        editUserId = null;
    })
    .catch(() => alert("Error"));
}

/* =========================
   EDIT USER
========================= */
function openEditUser(id) {

    editMode = true;
    editUserId = id;

    fetch(`${BASE_URL}/${id}`, {
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(res => res.json())
    .then(user => {

        username.value = user.username;
        password.value = "";
        email.value = user.email;
        firstName.value = user.firstName;
        lastName.value = user.lastName;
        jobTitle.value = user.jobTitle;
        salary.value = user.salary;
        role.value = user.role;

        openCreateUserModal();
    });
}

/* =========================
   DELETE USER
========================= */
function deleteUser(id) {

    if (!confirm("Delete user?")) return;

    fetch(`${BASE_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + getToken()
        }
    })
    .then(() => getUsers());
}

/* =========================
   MODAL
========================= */
function openCreateUserModal() {
    document.getElementById("userModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("userModal").style.display = "none";
}

/* =========================
   CLEAR FORM
========================= */
function clearForm() {
    username.value = "";
    password.value = "";
    email.value = "";
    firstName.value = "";
    lastName.value = "";
    jobTitle.value = "";
    salary.value = "";
    role.value = "USER";
}