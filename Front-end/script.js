const form = document.getElementById("formLogin");

form.addEventListener("submit", function(event) {
    event.preventDefault();

    let email = document.getElementById("email").value;
    let senha = document.getElementById("senha").value;

    if (email === "" || senha === "") {
        alert("Preencha todos os campos!");
    } else if (email === "cliente@gmail.com") {
        window.location.href = "perfil-cliente.html";
    } else if (email === "bibliotecario@gmail.com") {
        window.location.href = "perfil-bibliotecario.html";
    } else if (email === "adm@gmail.com") {
        window.location.href = "perfil-adm.html";
    } else {
        alert("Usuário não identificado!");
    }
});