const createHttpRequest = function() {
    let xmlHttp = null;
    try {
        // Firefox, Opera 8.0+, Safari
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        // Internet Explorer
        try {
            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {
                alert("您的浏览器不支持AJAX！");
            }
        }
    }
    return xmlHttp;
}

/**
 * 基本请求
 *
 * @param request 请求对象
 * @param method 方法类型 POST GET
 * @param path 请求地址
 * @param data formdata
 * @param callBack 状态回调函数 判断是否成功
 */
const baseApi = function (request,method,path,data,callBack){
    request.open(method,path);
    request.send(data);
    request.onreadystatechange = callBack;
}

/**
 * 文件API
 */
const fileApi = function (method,path,data,callBack){
    const request = createHttpRequest();
    return baseApi(request,method,path,data,callBack);
}

