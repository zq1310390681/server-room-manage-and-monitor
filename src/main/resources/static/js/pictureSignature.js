/**
 * Created by seky on 14/12/15.
 */



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

var myStage;
var flag=false;

function openPictureSignatureWindow(divName, width, height, imgDivName, imgWidth, imgHeight, imgUrlForSave, signBase30Url_Editer, signBase30Url_Previous, textareaID,isSelf) {

    var SWindow = document.createElement("div");
    SWindow.id = "psWindow";
    $(SWindow).appendTo($("#" + divName));
    $("#psWindow").kendoWindow({
        close: function(e) {
            // close animation has finished playing
            console.log(e)

            pswindow = $("#psWindow").data("kendoWindow");
            pswindow.destroy();
            pswindow.wrapper.remove();
        },
        animation: {
            close: {
                duration: 500
            }
        }
//            ,title: "手写"
        , width: width, height: height + 45, modal: true
//            ,pinned: true
        // ,position: { top: 200,left:400 }
        , resizable: false
    });
    var signatureTextdiv = document.createElement("textarea");
    $(signatureTextdiv)[0].style.position = "absolute";
    $(signatureTextdiv)[0].style.left = "0px";
    $(signatureTextdiv)[0].style.position = "0px";
    $(signatureTextdiv)[0].style.margin = "0px";
    $(signatureTextdiv)[0].style.padding = "0px";
    $(signatureTextdiv)[0].style.border = "none";
    var text_fontSize = 18;
    var text_fontfamily = "SimSun";
    try {
        console.log(textareaID);
        textvalue = $("#" + textareaID)[0].value;
        console.log("textvalue = ",textvalue)
        $(signatureTextdiv)[0].value = $("#" + textareaID)[0].value;
        var temp_fontsize = $("#" + textareaID).css("font-size");
        text_fontSize = temp_fontsize.substring(0, temp_fontsize.length - 2);
//        console.log(text_fontSize);
        text_fontfamily = $("#" + textareaID).css("font-family");
        console.log(text_fontfamily);

    }
    catch (e) {
        console.log(e);
    }
//    $(signatureTextdiv)[0].style.marginLeft="0px";
//    $(signatureTextdiv)[0].style.marginLeft="0px";
    $(signatureTextdiv)[0].style.width = width + 'px';
    $(signatureTextdiv)[0].style.height = height + "px";

    $(signatureTextdiv)[0].style.fontSize = text_fontSize * height / imgHeight + "px";
    $(signatureTextdiv)[0].style.fontFamily = text_fontfamily;
//    $(signatureTextdiv)[0].value="asasaspx";
    $(signatureTextdiv).appendTo($(SWindow));

    var signaturePlace = document.createElement("div");
    $(signaturePlace)[0].style.position = "absolute";
    $(signaturePlace)[0].style.left = "0px";
    $(signaturePlace)[0].style.position = "0px";
    $(signaturePlace)[0].style.background = "none";
    $(signaturePlace).attr("id", "pictureSignatureContainer");
    $(signaturePlace).appendTo($(SWindow));
    //判断是否为自己的笔记
    isSelfVal=$("#"+isSelf)[0].value;
    if(isSelfVal=="1"){
        flag=true;
    }
    myStage = initStage('pictureSignatureContainer', width, height);
    //console.log("stage inited~");

    if(signBase30Url_Editer!="" && signBase30Url_Editer!=undefined){
        loadBackgroundImages(signBase30Url_Editer,flag, myStage,{
            x:0,
            y:0,
            width:width,
            height:height
        });
    }


    var btn_contain = document.createElement("div");
    $(btn_contain)[0].style.position = "absolute";


    var excitBttn = document.createElement("a");
    excitBttn.className = "btn btn-xs btn-danger";
    $(excitBttn)[0].style.width = width / 4 - 20 + "px";
    var excitImg = document.createElement("i");
    excitImg.className = "icon-remove bigger-110";
    $(excitImg).appendTo($(excitBttn));
    var excitspan = document.createElement("span");
    excitspan.innerText = "取消签名";
    $(excitspan).appendTo($(excitBttn));
    excitBttn.onclick = function () {
        var dialog = $(SWindow).data("kendoWindow");
        dialog.close();
    };
//    resetBttn.style.float = 'right';
    $(excitBttn).appendTo($(btn_contain));
//    $(btn_contain1).appendTo($(btn_contain));


    var resetBttn = document.createElement("a");
    $(resetBttn)[0].style.width = width / 4 - 20 + "px";
    resetBttn.className = "btn btn-xs ";
    var undoImg = document.createElement("i");
    undoImg.className = "icon-reply bigger-110";
    $(undoImg).appendTo($(resetBttn));
    var undospan = document.createElement("span");
    undospan.innerText = " 插入签名";
    $(undospan).appendTo($(resetBttn));
    resetBttn.onclick = function () {

        addimage();

        //todo

    };
//    resetBttn.style.float = 'right';
    $(resetBttn).appendTo($(btn_contain));


    var submitBttn = document.createElement("a");
    $(submitBttn)[0].style.width = width / 4 - 20 + "px";
    submitBttn.className = "btn btn-xs btn-primary";
    var submitImg = document.createElement("i");
    submitImg.className = "icon-ok bigger-110";
    $(submitImg).appendTo($(submitBttn));
    var submitspan = document.createElement("span");
    submitspan.innerText = " 确认";
    $(submitspan).appendTo($(submitBttn));
    submitBttn.onclick = function () { // Note this is a function
        var sigdiv = $(signaturePlace);
            delCircle();//删除4个点
            myStage.toDataURL({
            callback: function (dataUrl) {
                $("#" + imgDivName)[0].src=dataUrl;
                $("#" + imgUrlForSave)[0].value=dataUrl;
                //$("#" + isSelf)[0].value="1";
            }
        });

        //todo 保存图片
        var dialog = $(SWindow).data("kendoWindow");
        dialog.close();
    };

    $(submitBttn).appendTo($(btn_contain));
    $(btn_contain)[0].style.left = (width - ($(btn_contain).width)) / 2 + "px";
    $(btn_contain)[0].style.top = height + 14 + "px";
    $(btn_contain).appendTo($(SWindow));

}

function update(activeAnchor) {
    var group = activeAnchor.getParent();

    var topLeft = group.find('.topLeft')[0];
    var topRight = group.find('.topRight')[0];
    var bottomRight = group.find('.bottomRight')[0];
    var bottomLeft = group.find('.bottomLeft')[0];
    var removeBtn = group.find('.topRightRemove')[0];
    var dateText = group.find('.dateText')[0];
    var image = group.find('.image')[0];

    var anchorX = activeAnchor.x();
    var anchorY = activeAnchor.y();

    // update anchor positions
    switch (activeAnchor.name()) {
        case 'topLeft':
            removeBtn.y(anchorY);
            bottomLeft.x(anchorX);
            break;
        case 'bottomRight':
            bottomLeft.y(anchorY);
            removeBtn.x(anchorX);
            dateText.x(anchorX);
            dateText.y(anchorY-20);
            break;
        case 'bottomLeft':
            bottomRight.y(anchorY);
            topLeft.x(anchorX);
            dateText.y(anchorY-20);
            break;

    }

    image.setPosition(topLeft.getPosition());

    var width = removeBtn.x() - topLeft.x();
    var height = bottomLeft.y() - topLeft.y();
    if(width && height) {
        image.setSize({width:width, height: height});
    }
}



function addAnchor(group, x, y, name) {
    var stage = group.getStage();
    var layer = group.getLayer();

    var anchor = new Kinetic.Circle({
        x: x,
        y: y,
        stroke: '#666',
        fill: '#ddd',
        strokeWidth: 2,
        radius: 8,
        name: name,
        draggable: true,
        dragOnTop: false
    });

    if (name.indexOf("Remove") > -1){
        anchor = new Kinetic.Circle({
            x: x,
            y: y,
            stroke: '#666',
            fill: '#f00',
            strokeWidth: 2,
            radius: 8,
            name: name,
            draggable: true,
            dragOnTop: false
        });
    }

    anchor.on('dragmove', function() {
        update(this);
        layer.draw();
    });
    anchor.on('mousedown touchstart', function() {
        group.setDraggable(false);
        this.moveToTop();
    });
    anchor.on('dragend', function() {
        group.setDraggable(true);
        layer.draw();
    });
    // add hover styling
    anchor.on('mouseover', function() {
        var layer = this.getLayer();
        document.body.style.cursor = 'pointer';
        this.setStrokeWidth(4);
        layer.draw();
    });
    anchor.on('mouseout', function() {
        console.log(this.name())
        var layer = this.getLayer();
        document.body.style.cursor = 'default';
        this.strokeWidth(2);
        layer.draw();
    });
    anchor.on('mouseup', function(e) {
        console.log("mouseup",this)
        console.log(this.name())
        var layer = this.getLayer();
        if(this.name().indexOf("Remove") > -1)
        {
            console.log("btn remove")
            this.getParent().hide();
            layer.draw();
        }
        //this.moveToTop();
    });

    group.add(anchor);
}


function loadImages(source,stage,options) {

    var image = new Image();
    image.onload = function(){
        loadImagesCallBack(image,stage,options);
    }
    image.src = source;
}


function loadBackgroundImages(source,flag,stage,options) {

    var image = new Image();
    image.onload = function(){
        loadBackgroundImagesCallBack(image,flag,stage,options);
    }
    image.src = source;
}

function initStage(containerName,width,height)
{
    console.log("initStage")
    var stage = new Kinetic.Stage({
        container: containerName,
        width: width,
        height: height
    });
    return stage;
}

function loadBackgroundImagesCallBack(image,flag,stage,options) {

    console.log("loadBackgroundImagesCallBack",image);
    var backgroundGroup;
    var backgroundImg;
    var layer = new Kinetic.Layer();
    if(!flag){
        backgroundGroup = new Kinetic.Group({
            x: options.x,
            y: options.y,
            draggable: false
        });
        layer.add(backgroundGroup);
        //layer.add(yodaGroup);
        stage.add(layer);

        // darth vader
        backgroundImg = new Kinetic.Image({
            x: 0,
            y: 0,
            image: image,
            width: options.width,
            height: options.height,
            name: 'image'
        });
        backgroundGroup.add(backgroundImg);
        stage.draw();
    }else{
        backgroundGroup = new Kinetic.Group({
            x: options.x,
            y: options.y,
            draggable: true
        });
        var layer = new Kinetic.Layer();
        layer.add(backgroundGroup);
        //layer.add(yodaGroup);
        stage.add(layer);

        // darth vader
        backgroundImg = new Kinetic.Image({
            x: 0,
            y: 0,
            image: image,
            width: options.width,
            height: options.height,
            name: 'image'
        });
        backgroundGroup.add(backgroundImg);
        addAnchor(backgroundGroup, 0, 0, 'topLeft');
        //addAnchor(darthVaderGroup, 200, 0, 'topRight');
        addAnchor(backgroundGroup, options.width, 0, 'topRightRemove');
        addAnchor(backgroundGroup, options.width, options.height, 'bottomRight');
        //addAnchor(backgroundGroup, 0, options.height, 'bottomLeft');

        backgroundGroup.on('dragstart', function() {
            this.moveToTop();
        });
        stage.draw();
    }

}

function loadImagesCallBack(image,stage,options) {
    // var stage = new Kinetic.Stage({
    //   container: 'container',
    //   width: options.width,
    //   height: options.height
    // });
    console.log("loadImagesCallBack",image)
    var signatureGroup = new Kinetic.Group({
        x: options.x,
        y: options.y,
        draggable: true
    });

    var layer = new Kinetic.Layer();

    /*
     * go ahead and add the groups
     * to the layer and the layer to the
     * stage so that the groups have knowledge
     * of its layer and stage
     */
    layer.add(signatureGroup);
    //layer.add(yodaGroup);
    stage.add(layer);

    // darth vader
    var signatureImg = new Kinetic.Image({
        x: 0,
        y: 0,
        image: image,
        width: options.width,
        height: options.height,
        name: 'image'
    });

    var d = new Date();

    var simpleText = new Kinetic.Text({
        x: options.width,
        y: options.height-20,
        text: d.toLocaleDateString(),
        fontSize: 20,
        name:"dateText",
        fontFamily: 'hei',
        fill: 'black'
    });

    signatureGroup.add(signatureImg);
    signatureGroup.add(simpleText);
    addAnchor(signatureGroup, 0, 0, 'topLeft');
    //addAnchor(darthVaderGroup, 200, 0, 'topRight');
    addAnchor(signatureGroup, options.width, 0, 'topRightRemove');
    addAnchor(signatureGroup, options.width, options.height, 'bottomRight');
    addAnchor(signatureGroup, 0, options.height, 'bottomLeft');

    signatureGroup.on('dragstart', function() {
        this.moveToTop();
    });


    stage.draw();
}
function addimage(){
    console.log("addimage")
    loadImages("/user/getSignImg/1", myStage,{
        x:100,
        y:100,
        width:200,
        height:100
    });
}
function delCircle() {

    var shapes = myStage.get('.topLeft')
    for(i=0;i<shapes.length;i++){
        console.log(shapes[i].getId());
        shapes[i].hide()
    }
    var shapes = myStage.get('.topRight')
    for(i=0;i<shapes.length;i++){
        console.log(shapes[i].getId());
        shapes[i].hide()
    }
    var shapes = myStage.get('.bottomRight')
    for(i=0;i<shapes.length;i++){
        console.log(shapes[i].getId());
        shapes[i].hide()
    }
    var shapes = myStage.get('.bottomLeft')
    for(i=0;i<shapes.length;i++){
        console.log(shapes[i].getId());
        shapes[i].hide()
    }
    var shapes = myStage.get('.topRightRemove')
    for(i=0;i<shapes.length;i++){
        console.log(shapes[i].getId());
        shapes[i].hide()
    }
    myStage.draw()


}