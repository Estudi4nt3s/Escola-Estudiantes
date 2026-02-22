// MENU
const openBtn = document.getElementById("openNotification");
const overlay = document.getElementById("notificationOverlay");
const closeBtn = document.getElementById("closeNotificationModal");

openBtn.addEventListener("click", () => {
    overlay.classList.add("show");
});

closeBtn.addEventListener("click", () => {
    overlay.classList.remove("show");
});

overlay.addEventListener("click", (e) => {
    if (e.target === overlay) {
        overlay.classList.remove("show");
    }
});
// CONFIRMA BOTAO
document.querySelectorAll(".confirm-btn").forEach(button => {
    button.addEventListener("click", function(e) {

        e.stopPropagation();

        const item = this.closest("li");

        // remove denied
        item.classList.remove("denied");

        // adiciona confirmado
        item.classList.add("confirmed");

        // muda texto
        item.querySelector("span").textContent = "Status: Confirmado";

        // muda botão
        this.textContent = "Confirmado";
        this.disabled = true;

    });
});