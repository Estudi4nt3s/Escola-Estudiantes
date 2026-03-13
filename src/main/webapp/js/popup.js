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

const popupInserir = document.getElementById("popupInserir")
const popupEditar = document.getElementById("popupEditar")
const popupExcluir = document.getElementById("popupExcluir")
const abrirInserir = document.getElementById("abrirInserir")

abrirInserir.onclick = () =>{
    popupInserir.style.display = "flex"
}

document.querySelectorAll(".fechar").forEach(btn=>{
    btn.onclick = ()=>{
        popupInserir.style.display="none"
        popupEditar.style.display="none"
        popupExcluir.style.display="none"
    }
})

document.querySelectorAll(".editar-btn").forEach(btn=>{

    btn.onclick = ()=>{

        document.getElementById("editarId").value = btn.dataset.id
        document.getElementById("editarTexto").value = btn.dataset.texto

        popupEditar.style.display="flex"

    }

})

document.querySelectorAll(".excluir-btn").forEach(btn=>{

    btn.onclick = ()=>{

        let id = btn.dataset.id

        document.getElementById("confirmarExcluir").href = contextPath + "/observacao?sub_acao=excluir&id="+id

        popupExcluir.style.display="flex"

    }

})