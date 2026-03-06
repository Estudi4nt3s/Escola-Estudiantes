function alternarVisibilidade(event) {
    event.stopPropagation();

    const iconeClicado = event.target;
    const menuAtual = iconeClicado.nextElementSibling;

    document.querySelectorAll('.popup').forEach(menu => {
        if (menu !== menuAtual) menu.classList.remove('show');
    });

    menuAtual.classList.toggle('show');
}

document.addEventListener('click', function() {
    document.querySelectorAll('.popup').forEach(menu => {
        menu.classList.remove('show');
    });
});