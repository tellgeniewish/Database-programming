window.onload = function () {
  document.getElementById("peopleSelection").style.display = "none";

  document.getElementById("menuOpen").addEventListener("click", function () {
    document.getElementById("sidebar").style.display = "block";
  });
  document.getElementById("menuClose").addEventListener("click", function () {
    document.getElementById("sidebar").style.display = "none";
  });

  document.getElementById("peopleSelectionOpen").addEventListener("click", function () {
    var open = document.getElementById("peopleSelectionOpen");
    var selection = document.getElementById("peopleSelection");

    if (selection.style.display == "none") {
      open.style.backgroundImage = `url(../images/close.png)`;
      selection.style.display = "block";
    } else {
      open.style.backgroundImage = `url(../images/open.png)`;
      selection.style.display = "none";
    }
  });
};
