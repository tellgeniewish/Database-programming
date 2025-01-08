const $target = document.querySelector(".cardContainer");

const kit = {
  name: "[쿡솜씨] 담양청국장 밀키트 2인분",
  price: "9,600",
  desc: "청국장의 맛은 살리고 냄새는 조절한 담양청국장",
};

let arr = new Array(8);
arr = arr.fill(kit);

$target.innerHTML = `
${arr
  .map((val) => {
    return `
      <div id="card">
        <img id="cardImg" src="../images/example.png" />
        <div id="cardText">
          <div id="cardName">${val.name}</div>
          <div id="cardPrice">${val.price}원</div>
          <div id="cardDesc">${val.desc}</div>
        </div>
      </div>
    `;
  })
  .join("")}
`;
