const form = document.getElementById("formLogin");
""
form.addEventListener("submit", function(event) {
    event.preventDefault();

    let email = document.getElementById("email").value;
    let senha = document.getElementById("senha").value;

    if (email === "" || senha === "") {
        alert("Preencha todos os campos!");
    } else {
        alert("Login realizado com sucesso!");
    }
});