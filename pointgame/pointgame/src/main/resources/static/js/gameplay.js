const gameCloseBtn = document.getElementById("gameCloseBtn");

const msgContainer = document.getElementById("msg-container");
const msgTitle = document.getElementById("msgTitle");
const msg = document.getElementById("msg");
const msgValue = msg.value;
const finishMsg = document.getElementById("finishMsg");

const closeBtn = document.getElementById("closeBtn");
const inputNum = document.getElementById("inputNum");
const checkBtn = document.getElementById("checkBtn");

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
