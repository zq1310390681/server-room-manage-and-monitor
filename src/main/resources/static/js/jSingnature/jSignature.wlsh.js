/*
使用前确认导入必要的文件
1.kendoui.js
2.jSignature.js
3.ace.css

divName:需要弹出并用来签名的div名称
 width：需要进行签名div的width
 height：需要进行签名div的height
 imgDivName：导出生成图片的div  id


 copyright---AlvinWei---shou
*/

    function openSignatureWindow(divName,width,height,imgDivName,imgWidth,imgHeight,imgUrlForSave,signBase30Url_Editer,signBase30Url_Previous,textareaID) {

    var SWindow = document.createElement("div");
    SWindow.id = "sWindow";
    $(SWindow).appendTo($("#" + divName));
    $("#sWindow").kendoWindow({
        animation: {
            close: {
                duration: 500
            }
        }
//            ,title: "手写"
        , width: width, height: height+45, modal: true
//            ,pinned: true
        // ,position: { top: 200,left:400 }
        , resizable: false
    });
//        var dialog = $("#"+divName).data("kendoWindow");
//        dialog.center();
    var signatureTextdiv= document.createElement("textarea");
    $(signatureTextdiv)[0].style.position="absolute";
    $(signatureTextdiv)[0].style.left="0px";
    $(signatureTextdiv)[0].style.position="0px";
    $(signatureTextdiv)[0].style.margin="0px";
    $(signatureTextdiv)[0].style.padding="0px";
    $(signatureTextdiv)[0].style.border="none";
    var text_fontSize=18;
    var text_fontfamily="SimSun";
    try{
        $(signatureTextdiv)[0].value=$("#"+textareaID)[0].value;
        var temp_fontsize=$("#"+textareaID).css("font-size");
        text_fontSize=temp_fontsize.substring(0,temp_fontsize.length-2);
//        console.log(text_fontSize);
        text_fontfamily=$("#"+textareaID).css("font-family");
        console.log(text_fontfamily);

    }
    catch (e){
//        console.log(e);
    }
//    $(signatureTextdiv)[0].style.marginLeft="0px";
//    $(signatureTextdiv)[0].style.marginLeft="0px";
    $(signatureTextdiv)[0].style.width=width+ 'px';
    $(signatureTextdiv)[0].style.height=height+"px";

    $(signatureTextdiv)[0].style.fontSize=text_fontSize*height/imgHeight+"px";
    $(signatureTextdiv)[0].style.fontFamily=text_fontfamily;
//    $(signatureTextdiv)[0].value="asasaspx";
    $(signatureTextdiv).appendTo($(SWindow));

    var signaturePlace = document.createElement("div");
    $(signaturePlace)[0].style.position="absolute";
    $(signaturePlace)[0].style.left="0px";
    $(signaturePlace)[0].style.position="0px";
    $(signaturePlace)[0].style.background="none";
    $(signaturePlace).appendTo($(SWindow));
    $(signaturePlace).jSignature(
        {
            'width':  width+ 'px', 'height': height+ 'px', 'color': '#000', 'background-color': '#fff', 'decor-color': '#fff', 'lineWidth': 1, 'minFatFingerCompensation': -10, 'showUndoButton': true

        }
    )
    $(signaturePlace).jSignature("reset") // clears the canvas and rerenders the decor on it.
//    var datapair1 = $(signaturePlace).jSignature("getData","base30");
    if(signBase30Url_Editer!=null){
        console.log(signBase30Url_Editer);
        try{
            $(signaturePlace).jSignature("setData",signBase30Url_Editer);
        }
        catch(err)
        {
            try{
                $(signaturePlace).jSignature("setData","data:"+signBase30Url_Editer);
            }
            catch(err) {
                console.log(err);
            }
        }
    }
    var btn_contain=document.createElement("div");
    $(btn_contain)[0].style.position="absolute";

//    btn_contain.style.float="right"
//    btn_contain.style.length="1000"
//    var btn_contain1=document.createElement("div");
//    btn_contain1.style.width="300px";


    var excitBttn = document.createElement("a");
    excitBttn.className = "btn btn-xs btn-danger";
    $(excitBttn)[0].style.width=width/4-20+"px";
    var excitImg = document.createElement("i");
    excitImg.className = "icon-remove bigger-110";
    $(excitImg).appendTo($(excitBttn));
    var excitspan = document.createElement("span");
    excitspan.innerText = " 取消签字";
    $(excitspan).appendTo($(excitBttn));
    excitBttn.onclick = function () {
        var dialog = $(SWindow).data("kendoWindow");
        dialog.close();
    };
//    resetBttn.style.float = 'right';
    $(excitBttn).appendTo($(btn_contain));
//    $(btn_contain1).appendTo($(btn_contain));

    var resetAllBttn = document.createElement("a");
    $(resetAllBttn)[0].style.width=width/4-20+"px";
    resetAllBttn.className = "btn btn-xs btn-yellow";
    var undoAllImg = document.createElement("i");
    undoAllImg.className = "icon-undo bigger-110";
    $(undoAllImg).appendTo($(resetAllBttn));
    var undoAllspan = document.createElement("span");
    undoAllspan.innerText = " 重置本人笔迹";
    $(undoAllspan).appendTo($(resetAllBttn));
    resetAllBttn.onclick = function () {
        $(signaturePlace).jSignature("reset")
        if(signBase30Url_Editer!=null){
            try{
                $(signaturePlace).jSignature("setData",signBase30Url_Previous);
            }
            catch(err)
            {
                try{
                    $(signaturePlace).jSignature("setData","data:"+signBase30Url_Previous);
                }
                catch(err) {
                    console.log(err);
                }
            }
        }
    };
//    resetBttn.style.float = 'right';
    $(resetAllBttn).appendTo($(btn_contain));

    var resetBttn = document.createElement("a");
    $(resetBttn)[0].style.width=width/4-20+"px";
    resetBttn.className = "btn btn-xs ";
    var undoImg = document.createElement("i");
    undoImg.className = "icon-reply bigger-110";
    $(undoImg).appendTo($(resetBttn));
    var undospan = document.createElement("span");
    undospan.innerText = " 撤销本次编辑笔迹";
    $(undospan).appendTo($(resetBttn));
    resetBttn.onclick = function () {
        $(signaturePlace).jSignature("reset")
        if(signBase30Url_Editer!=null){
            try{
                $(signaturePlace).jSignature("setData",signBase30Url_Editer);
            }
            catch(err)
            {
                try{
                    $(signaturePlace).jSignature("setData","data:"+signBase30Url_Editer);
                }
                catch(err) {
                    console.log(err);
                }
            }
        }
    };
//    resetBttn.style.float = 'right';
    $(resetBttn).appendTo($(btn_contain));


    var submitBttn = document.createElement("a");
    $(submitBttn)[0].style.width=width/4-20+"px";
    submitBttn.className = "btn btn-xs btn-primary";
    var submitImg = document.createElement("i");
    submitImg.className = "icon-ok bigger-110";
    $(submitImg).appendTo($(submitBttn));
    var submitspan = document.createElement("span");
    submitspan.innerText = " 确认";
    $(submitspan).appendTo($(submitBttn));
    submitBttn.onclick = function () { // Note this is a function
        var sigdiv = $(signaturePlace);
        var datapairimage = sigdiv.jSignature("getData", "image");
        var datapairbase30 = sigdiv.jSignature("getData", "base30");
        var image = new Image(imgWidth, imgHeight);
//        i.id=imgID;
//        i.width=imgWidth;
//        i.height=imgHeight;

        image.src = "data:" + datapairimage[0] + "," + datapairimage[1]
        image.style.position="absolute";
//        $(i).appendTo($("#" + imgDivName)) // append the image (SVG) to DOM.
//        $("#" + imgDivName).html($(datapairimage));
        $("#" + imgDivName)[0].src=image.src;
        $("#" + imgUrlForSave)[0].value="data:" + datapairbase30[0] + "," + datapairbase30[1];
//        $("#counterSign_Content")[0].innerText="data:" + datapairbase30[0] + "," + datapairbase30[1]
        console.log($("#" + imgDivName)[0].src);
        console.log($("#" + imgUrlForSave)[0].value);
        var dialog = $(SWindow).data("kendoWindow");
        dialog.close();




    };

    $(submitBttn).appendTo($(btn_contain));
    $(btn_contain)[0].style.left=(width-($(btn_contain).width))/2+"px";
    $(btn_contain)[0].style.top=height+14+"px";
    $(btn_contain).appendTo($(SWindow));

}