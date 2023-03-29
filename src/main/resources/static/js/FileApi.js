
/**
 * 文件上传
 * @param file
 */
function fileUpload(file) {
    console.log("上传")
    console.log(file)
    try {
        if (window.FileReader) {
            const fReader = new FileReader();
            let request = createHttpRequest;
            request.setRequestHeader("content-type", "multipart/form-data"); //流类型
            // 结果回调函数
            const callBk = function () {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        console.log("文件上传成功")
                    } else {
                        console.log("文件上传失败了")
                    }
                }
            }
            // 组装请求参数
            let fd = new FormData()
            fd.append('file', file);
            // 发起请求
            fReader.onload = baseApi(request,"POST",api_uploadFile,fd,callBk);
            fReader.readAsArrayBuffer(file);
        } else {
            console.log("浏览器不支持上传文件")
        }
    } catch (e) {
        console.log("文件上传失败")
        console.error(e)
    }
}

