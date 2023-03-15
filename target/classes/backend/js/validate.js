
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (val == null) {
    return false
  } else {
    return true
  }
}

//校验账号
function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("Your username length should be 3-20!"))
  } else {
    callback()
  }
}

//校验姓名
function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please enter your name"))
  } else if (value.length > 12) {
    callback(new Error("Your name should length should be 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){

  if (value == "") {
    callback(new Error("Please enter your mobile phone number"))
  } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
    callback(new Error("Please enter the correct mobile phone number! "))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {

  let reg = /^RG\d{4}$/
  if(value == '') {
    callback(new Error('Please enter your staff ID'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('Incorrect Staff ID number，like RGXXXX'))
  }
}