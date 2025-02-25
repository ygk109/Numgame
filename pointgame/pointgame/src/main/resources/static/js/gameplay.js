const gameCloseBtn = document.getElementById("gameCloseBtn");

const msgContainer = document.getElementById("msg-container");
const msgTitle = document.getElementById("msgTitle");
const msg = document.getElementById("msg");
const msgValue = msg.value;
const finishMsg = document.getElementById("finishMsg");

const closeBtn = document.getElementById("closeBtn");
const inputNum = document.getElementById("inputNum");
const checkBtn = document.getElementById("checkBtn");

const errorMsg = document.getElementById("errorMsg");

//input check and message
function validateInput() {
  if (inputNum.value == "") {
    errorMsg.innerText = "各桁を 0 ~ 9 の数字で入力します。";
    return false;
  }
  if (!/^\d{3}$/.test(inputNum.value)) {
    errorMsg.innerText = "各桁を 0 ~ 9 の数字で入力します。";
    return false;
  }
  let digits = inputNum.value.split("");
  let uniqueDigits = new Set(digits);

  if (uniqueDigits.size !== digits.length) {
    errorMsg.innerText = "１桁目から3桁目まで数字重複は入力不可です";
    return false;
  } else {
    errorMsg.innerText = "";
    return true;
  }
}

checkBtn.addEventListener("click", function (event) {
  if (!validateInput()) {
    event.preventDefault(); // 입력이 잘못되면 폼 제출 방지
  }
});

//game close fcuntion
function gameClose() {
  const confirmed = confirm("ゲームを終了しますか");
  if (confirmed) {
    window.location.href = "/login";
  }
}
gameCloseBtn.addEventListener("click", gameClose);

//pop-up message function
function openMsg() {
  msgContainer.style.display = "flex";
  msgContainer.style.flexDirection = "column";
  msgContainer.style.justifyContent = "center";
  msgContainer.style.alignItems = "center";
  finishMsg.style.display = "block";
}

if (msgValue == "1") {
  openMsg();
}

function closeMsg() {
  msgContainer.style.display = "none";
  inputNum.disabled = true;
  checkBtn.disabled = true;
}
closeBtn.addEventListener("click", closeMsg);

function checkInput() {
  if (inputNum.disabled == true) {
    return false;
  }
  return true;
}
