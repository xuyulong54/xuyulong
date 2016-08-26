function setTab(m, n) {
    var tli = document.getElementById("Mu" + m).getElementsByTagName("li");
    var mli = document.getElementById("cont" + m).getElementsByTagName("ul");
    for (i = 0; i < tli.length; i++) {
        tli[i].className = i == n ? "hover" : "";
        mli[i].style.display = i == n ? "block" : "none";
    }
}