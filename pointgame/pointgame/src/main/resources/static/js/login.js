const idPattern = /^[0-9]{6}$/;
const pwPattern = /^[A-Za-z0-9]{8}$/;

const userIdInput = document.getElementById("userId");
const userPwInput = document.getElementById("userPw");
const message = document.getElementById("message");

const loginForm = document.querySelector("form");

function inputCheck(event) {
  event.preventDefault();

  let userId = userIdInput.value.trim();
  let userPw = userPwInput.value.trim();
  let errorMessage = "";
  let focus = null;

  if (userId == "") {
    errorMessage += "IDは半角数字の6桁で入力してください。<br>";
    if (!focus) {
      focus = userIdInput;
    }
  } else if (!idPattern.test(userId)) {
    errorMessage += "IDは半角数字以外は入力できません。<br>";
    if (!focus) {
      focus = userIdInput;
    }
  }

  if (userPw == "") {
    errorMessage += "PWは半角英字、数字の8桁で入力してください。";
    if (!focus) {
      focus = userPwInput;
    }
  } else if (!pwPattern.test(userPw)) {
    errorMessage += "PWは半角英数字以外は入力できません。";
    if (!focus) {
      focus = userPwInput;
    }
  }

  if (errorMessage !== "") {
    message.innerHTML = errorMessage;
    message.style.display = "block";
    if (focus) focus.focus();
  } else {
    message.style.display = "none";
    loginForm.submit();
  }
}

loginForm.addEventListener("submit", inputCheck);
