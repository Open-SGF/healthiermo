
var textInfo = {}

function loadText() {
    $.ajax({
        url : '/text',
        type : 'GET',
        dataType:'json',
        success : function(data) {              
           textInfo = data;
        }
    });
}

function getText(box, pie) {

    for(let i = 0; i < textInfo.length; i++) {
        let info = textInfo[i];
        if(info.box == box && info.pie == pie) {    

            let text = info.text;
            
            while(true) {
                let start = text.indexOf("[")
                if(start == -1) {
                    break;
                }

                let end = text.indexOf("]")
                if(end == -1) {
                    break;
                }
                
                let split = text.substring(start + 1, end).split("|")
                
                let linkText = split[0]
                let linkLiteral = split[1]

                let newLink = `<a href=\"${linkLiteral}\" target="_blank">${linkText}</a>`
                console.log(newLink)

                text = text.replace(text.substring(start, end + 1), newLink)
                //console.log("Text now: "+ text)

                if(!text.includes("[")) { 
                    break
                }

            }


            text = text.replace(/(?:\\[rn]|[\r\n])/g, "<br>")
            text = text.replace('\n', "<br>")
            text = text.replace('\r', "<br>")

            console.log("Final:" + text)

            return [info.title, text]
        }
    }
    
    return ["Could not find text for the combination " + box + ":" + pie +". Please add within the panel.", ""];
}
loadText()

var modal = new tingle.modal({
    footer: true,
    onClose: function() {
        if(audioPlaying) {
            $('#audioFile').get(0).pause();
            $('#audioFile').get(0).currentTime = 0;
        }
        console.log('close');
        isOpen = false;
    },
    onOpen: function() {
        isOpen = true;
    },
    beforeOpen: function() {
    },
    beforeClose: function() {
        console.log('before close');
        return true;
    },
    cssClass: ['modalFont']
});

var isOpen = false;
var audioPlaying = false;

modal.addFooterBtn('Play Audio', 'tingle-btn tingle-btn--primary tingle-btn--pull-right', function() {
    $('#audioFile').get(0).play();
    audioPlaying = true;
});

modal.addFooterBtn('Close', 'tingle-btn tingle-btn--default tingle-btn--pull-right', function(){
    modal.close();
    if(audioPlaying) {
        $('#audioFile').get(0).pause();
        $('#audioFile').get(0).currentTime = 0;
        audioPlaying = false;
    }
});

function pointIsInPoly(p, polygon) {
    var isInside = false;
    var minX = polygon[0][0], maxX = polygon[0][0];
    var minY = polygon[0][1], maxY = polygon[0][1];
    for (var n = 1; n < polygon.length; n++) {
        var q = polygon[n];
        minX = Math.min(q[0], minX);
        maxX = Math.max(q[0], maxX);
        minY = Math.min(q[1], minY);
        maxY = Math.max(q[1], maxY);
    }

    if (p[0] < minX || p[0] > maxX || p[1] < minY || p[1] > maxY) {
        return false;
    }

    var i = 0, j = polygon.length - 1;
    for (i, j; i < polygon.length; j = i++) {
        if ( (polygon[i][1] > p[1]) != (polygon[j][1] > p[1]) &&
                p[0] < (polygon[j][0] - polygon[i][0]) * (p[1] - polygon[i][1]) / (polygon[j][1] - polygon[i][1]) + polygon[i][0] ) {
            isInside = !isInside;
        }
    }

    return isInside;
}

function remap(x, in_min, in_max, out_min, out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
}

function rotatePoint(point, center, angle){
    var pX = point[0];
    var pY = point[1];
    angle = (angle ) * (Math.PI/180); // Convert to radians

    var s = Math.sin(angle);
    var c = Math.cos(angle);

    pX -= center[0];
    pY -= center[1];

    var rotatedX = pX * c - pY * s;
    var rotatedY = pX * s + pY * c;

    rotatedX += center[0];
    rotatedY += center[1];
    return [rotatedX,rotatedY];
}

function getAngle(p1, p2) {
    return Math.atan2(p2[1] - p1[1], p2[0] - p1[0]) / (Math.PI / 180);
}

function interpolate(a, b, frac) {
    var nx = a[0]+(b[0]-a[0])*frac;
    var ny = a[1]+(b[1]-a[1])*frac;
    return [nx, ny];
}

$(window).on('resize', function(){
    var size = window.innerHeight;
    if (window.innerWidth < size) { 
        size = window.innerWidth;
    }

    size -= (size / 100) < 20 ? 20 : (size / 100);

    $(".ringsizer").width(size);
    $(".ringsizer").height(size);
});

$(document).ready(function() {
    $(window).resize();

    var initialPositions = {};
    $(".ringpart").each(function(idx, item){
        initialPositions[$(item).attr("id")] = [$(item).offset().left, $(item).offset().top];
    });

    var draggableItemLocations = {
        "ring7": [[1862,519], [1917,593], [1985,705], [2040,866], [2070,1037], [2068,1187], [2048,1328], [1816,1273], [1838,1136], [1824,998], [1787,861], [1742,758], [1679,664]],
        "ring6": [[1858,511], [1818,464], [1748,395], [1614,293], [1461,218], [1359,183], [1247,159], [1111,149], [1111,387], [1239,401], [1365,436], [1455,477], [1530,523], [1585,566], [1620,601], [1671,660]],
        "ring5": [[1103,152], [1105,388], [980,398], [909,413], [815,447], [775,466], [681,525], [630,565], [579,610], [540,659], [351,511], [426,431], [504,360], [610,289], [701,240], [783,207], [872,179], [978,160]],
        "ring4": [[349,516], [296,587], [245,675], [202,779], [166,897], [143,1047], [141,1184], [166,1329], [396,1271], [379,1182], [382,1043], [398,940], [430,846], [471,758], [534,663]],
        "ring3": [[398,1280], [166,1333], [190,1421], [225,1512], [267,1590], [327,1682], [355,1724], [416,1791], [489,1857], [549,1903], [614,1946], [685,1983], [789,1769], [665,1694], [538,1572], [449,1429]],
        "ring2": [[791,1771], [689,1991], [779,2026], [870,2054], [968,2070], [1094,2081], [1196,2079], [1290,2062], [1377,2042], [1461,2013], [1522,1987], [1416,1771], [1329,1804], [1237,1832], [1121,1842], [1037,1840], [933,1820], [856,1798]],
        "ring1": [[1813,1281], [2050,1334], [2011,1448], [1966,1554], [1903,1660], [1834,1749], [1785,1800], [1722,1861], [1654,1910], [1603,1941], [1528,1984], [1426,1768], [1502,1725], [1595,1654], [1677,1568], [1738,1477], [1787,1373]],
    };

    var targetItemLocations = {
        "layer2": [[1104,1113], [1647,804], [1697,904], [1725,1010], [1732,1132], [1722,1254], [1688,1344], [1647,1426]],
        "layer3": [[1110,1119], [1647,1429], [1560,1554], [1435,1651], [1307,1710], [1194,1735], [1107,1741]],
        "layer4": [[1107,1116], [1104,1744], [948,1722], [826,1676], [713,1604], [632,1522], [563,1432]],
        "layer5": [[1107,1116], [560,1426], [495,1269], [482,1147], [485,1035], [507,929], [535,854], [563,804]],
        "layer6": [[1107,1113], [563,804], [635,701], [713,626], [801,566], [913,519], [1016,494], [1104,488]],
        "layer7": [[1107,488], [1110,1110], [1651,801], [1579,704], [1463,597], [1326,526], [1219,497]],
    };

    var ringCenterPoints = {
        "ring7": [1940, 950],
        "ring6": [1460, 350],
        "ring5": [730, 360],
        "ring4": [300, 920],
        "ring3": [450, 1640],
        "ring2": [1100, 1950],
        "ring1": [1770, 1635],
    };

    var clickableSpots = {
        "outer_ring": [[1121,45],[1227,51],[1335,71],[1457,102],[1559,143],[1638,189],[1714,234],[1803,307],[1865,360],[1899,397],[1954,462],[2009,548],[2056,625],[2103,725],[2135,827],[2160,941],[2172,1067],[2176,1165],[2162,1259],[2144,1365],[2115,1466],[2087,1536],[2066,1584],[2017,1666],[1944,1780],[1895,1839],[1820,1912],[1712,1992],[1626,2045],[1529,2092],[1433,2130],[1341,2153],[1209,2177],[1048,2183],[954,2175],[838,2151],[702,2104],[575,2039],[457,1961],[382,1902],[321,1839],[266,1776],[192,1666],[144,1578],[105,1487],[74,1377],[58,1316],[48,1236],[38,1051],[48,963],[70,861],[105,735],[166,611],[239,489],[323,389],[396,318],[453,265],[561,195],[673,136],[777,98],[883,67],[975,49],[1111,43],[1109,147],[973,157],[877,175],[820,189],[628,277],[492,365],[384,470],[333,536],[258,650],[190,798],[150,957],[139,1141],[160,1308],[188,1428],[252,1570],[341,1703],[457,1831],[622,1951],[750,2012],[940,2067],[1064,2077],[1194,2081],[1368,2039],[1488,2000],[1573,1963],[1683,1890],[1812,1772],[1907,1658],[1974,1542],[2013,1452],[2050,1326],[2072,1177],[2070,1012],[2044,866],[1977,686],[1911,576],[1826,470],[1746,389],[1620,297],[1461,216],[1362,179],[1221,153],[1115,147],[1109,147]],
        "inner_ring": [[1109,385],[1231,397],[1335,422],[1427,460],[1506,507],[1602,580],[1679,660],[1732,741],[1789,862],[1826,998],[1836,1128],[1816,1273],[1779,1391],[1722,1499],[1649,1593],[1594,1646],[1506,1719],[1410,1770],[1298,1811],[1199,1829],[1080,1837],[958,1819],[852,1790],[793,1762],[695,1705],[622,1646],[575,1599],[508,1517],[449,1411],[419,1336],[396,1246],[388,1161],[386,1061],[394,978],[419,892],[447,817],[484,749],[528,676],[579,615],[643,552],[710,503],[795,456],[895,416],[993,395],[1099,385],[1107,481],[1031,487],[968,495],[909,515],[838,538],[775,578],[697,633],[622,705],[565,786],[528,862],[500,945],[482,1033],[482,1124],[490,1216],[504,1289],[535,1371],[565,1428],[616,1499],[685,1578],[797,1654],[873,1694],[960,1719],[1042,1735],[1133,1737],[1256,1723],[1376,1680],[1467,1627],[1537,1566],[1600,1509],[1643,1440],[1695,1346],[1726,1230],[1738,1130],[1730,1016],[1706,925],[1687,859],[1655,796],[1602,721],[1547,662],[1488,607],[1416,562],[1325,519],[1235,497],[1162,485],[1109,485],[1107,481]],
        "layer2": [[1104,1113], [1647,804], [1697,904], [1725,1010], [1732,1132], [1722,1254], [1688,1344], [1647,1426]],
        "layer3": [[1110,1119], [1647,1429], [1560,1554], [1435,1651], [1307,1710], [1194,1735], [1107,1741]],
        "layer4": [[1107,1116], [1104,1744], [948,1722], [826,1676], [713,1604], [632,1522], [563,1432]],
        "layer5": [[1107,1116], [560,1426], [495,1269], [482,1147], [485,1035], [507,929], [535,854], [563,804]],
        "layer6": [[1107,1113], [563,804], [635,701], [713,626], [801,566], [913,519], [1016,494], [1104,488]],
        "layer7": [[1107,488], [1110,1110], [1651,801], [1579,704], [1463,597], [1326,526], [1219,497]],
    };

    var currentRotation = 0;

    $("#coolcontainer").on("mousedown touchstart", function(event) {
        
        if(isOpen) {
            return
        }

        event.preventDefault();

        var pageX = event.pageX;
        var pageY = event.pageY;
        
        if (event.type == "touchstart") {
            pageX = event.targetTouches[0].pageX;
            pageY = event.targetTouches[0].pageY;
        }

        var x = pageX - $('#coolcontainer').offset().left;
        var y = pageY - $('#coolcontainer').offset().top;

        var curRingWidth = $("#coolcontainer").width();
        var curRingHeight = $("#coolcontainer").height();

        var newX = remap(x, 0, curRingWidth, 0, 2224);
        var newY = remap(y, 0, curRingHeight, 0, 2224);

        var onClickItem = "";
        $.each(Object.keys(clickableSpots), function(idx, key){
            if (pointIsInPoly([newX, newY], clickableSpots[key])) {
                onClickItem = key;
                return false;
            }
        });

        var ogpx = 0, ogpy = 0;
        if (onClickItem != "") {
            // todo: check deltas between down and up event, if > xyz then maybe this was just a badly placed drag
            var mouseUp = false;
            var checkOnMouseUp = function() {
                if(mouseUp) {
                    document.onmouseup = null; 
                } else {
                    document.ontouchend = null; 
                }
                
                
                if(onClickItem == "outer_ring") {
                    
                    let box = "MISC"
                    let pie = "OUTER_RING"
                    
                    document.getElementById("audioFile").src = "/audio-files/" + box + "-" + pie + ".wav";
    
                    console.log("Outer ring being called")
                    
                    
                    let boxInfo = getText(box, pie)
                    console.log(boxInfo)
                    let title = boxInfo[0]
                    let text = boxInfo[1]
    
                    modal.setContent(`
                    <h1>${title}</h1>
                    <p>${text}</p>
                    `); 
                
                    modal.open();
                    
                    return
                }
                
                 if(onClickItem == "inner_ring") {
                    
                    let box = "MISC"
                    let pie = "INNER_RING"            
                    
                    document.getElementById("audioFile").src = "/audio-files/" + box + "-" + pie + ".wav";
    
                    console.log("Inner ring being called");
                    
                    let boxInfo = getText(box, pie)
                    console.log(boxInfo)
                    let title = boxInfo[0]
                    let text = boxInfo[1]
    
                    modal.setContent(`
                    <h1>${title}</h1>
                    <p>${text}</p>
                    `); 
                
                    modal.open();
                    
                    return
                }
                    
                
                let box = "INNER"
                let pie =  $.trim($("#" + onClickItem).attr("alt"))
                
                document.getElementById("audioFile").src = "/audio-files/" + box + "-" + pie + ".wav";

                console.log("Box " + box + " Pie:" + pie)
                
                let boxInfo = getText(box, pie)
                console.log(boxInfo)
                let title = boxInfo[0]
                let text = boxInfo[1]

                modal.setContent(`
                <h1>${title}</h1>
                <p>${text}</p>
                `); 
            
                modal.open();
            };

            if (event.type == "mousedown") {
                ogpx = event.clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
                ogpy = event.clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);

                document.onmouseup = checkOnMouseUp;
                mouseUp = true;
            } else {
                ogpx = event.targetTouches[0].clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
                ogpy = event.targetTouches[0].clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);

                document.ontouchend = checkOnMouseUp;
            }

            console.log("on click item:", onClickItem);
            return;
        }

        console.log("mousedown pre rotate", x, y);

        var rotatedXY = rotatePoint([x, y], [curRingWidth / 2, curRingHeight / 2], -currentRotation);
        x = rotatedXY[0];
        y = rotatedXY[1];

        console.log("mousedown pre remap", x, y);

        var newX = remap(x, 0, curRingWidth, 0, 2224);
        var newY = remap(y, 0, curRingHeight, 0, 2224);

        console.log("mousedown post remap", newX, newY);

        var selectedItem = "";
        $.each(Object.keys(draggableItemLocations), function(idx, key){
            if (pointIsInPoly([newX, newY], draggableItemLocations[key])) {
                selectedItem = key;
                return false;
            }
        });

        if (selectedItem == "")
            return

        if (event.type == "mousedown") {
            ogpx = event.clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
            ogpy = event.clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);

            document.onmouseup = closeDragElement;
            document.onmousemove = elementDrag;
        } else {
            ogpx = event.targetTouches[0].clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
            ogpy = event.targetTouches[0].clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);

            document.ontouchend = closeDragElement;
            document.ontouchmove = elementDrag;
        }

        var oga = Math.atan2(ogpy,ogpx) * 180 / Math.PI + 90;
        if(ogpy <= 0 && ogpx < 0){oga = 360 + oga;}

        oga -= currentRotation;

        $("#" + selectedItem).css("-webkit-filter", "drop-shadow(0px 0px 10px rgba(0, 0, 0, 1))");
        $("#" + selectedItem).css("filter", "drop-shadow(0px 0px 10px rgba(0, 0, 0, 1))");


        function highlightPieItem(){
            var currentFingerPoint = ringCenterPoints[selectedItem];

            var remappedFingerX = remap(currentFingerPoint[0], 0, 2224, 0, curRingWidth);
            var remappedFingerY = remap(currentFingerPoint[1], 0, 2224, 0, curRingHeight);

            var remappedFingerXY = [remappedFingerX, remappedFingerY];

            remappedFingerXY = rotatePoint(remappedFingerXY, [curRingWidth / 2, curRingHeight / 2], currentRotation);

            var midInterp = interpolate(remappedFingerXY, [curRingWidth / 2, curRingHeight / 2], 0.6);

            var forwardRotatedXY = [0, 0];
            forwardRotatedXY[0] = remap(midInterp[0], 0, curRingWidth, 0, 2224);
            forwardRotatedXY[1] = remap(midInterp[1], 0, curRingHeight, 0, 2224);

            var targetItem = "";
            $.each(Object.keys(targetItemLocations), function(idx, key){
                if (pointIsInPoly(forwardRotatedXY, targetItemLocations[key])) {
                    targetItem = key;
                    return false;
                }
            });

            if (targetItem == "") {
                return;
            }

            var doWeHaveHighlight = $("#" + targetItem).css("-webkit-filter") == "";
            if(!doWeHaveHighlight) {
                $(".innerpie").css("-webkit-filter", "");
                $(".innerpie").css("filter", "");
                $(".innerpie").css({zIndex: ''});
                
                $("#" + targetItem).css("z-index", "10000");
                $("#" + targetItem).css("-webkit-filter", "drop-shadow(0px 0px 10px rgba(0, 0, 0, 1))");
                $("#" + targetItem).css("filter", "drop-shadow(0px 0px 10px rgba(0, 0, 0, 1))");
            }
        }

        function elementDrag(e) {
            e = e || window.event;

            var curRingWidth = $("#coolcontainer").width();
            var curRingHeight = $("#coolcontainer").height();

            var px = 0, py = 0;
            // calculate the new ring rotation:
            if (e.type == "touchmove") {
                px = e.targetTouches[0].clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
                py = e.targetTouches[0].clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);
            } else {
                e.preventDefault();

                px = e.clientX - $("#coolcontainer").offset().left - (curRingWidth / 2);
                py = e.clientY - $("#coolcontainer").offset().top - (curRingHeight / 2);
            }

            var a = Math.atan2(py,px) * 180 / Math.PI + 90;
            if(py <= 0 && px < 0){a = 360 + a;}
            a -= oga;
            currentRotation = a;
            if (currentRotation > 360) {
                currentRotation -= 360;
            }

            if (currentRotation < -360) {
                currentRotation += 360;
            }

            $(".outerring").css("-webkit-transform","rotate(" + a + "deg)");
            highlightPieItem(); 
        }
        

        function closeDragElement(e) {
            // stop moving when mouse button is released:
            document.onmouseup = document.ontouchend = null;
            document.onmousemove = document.ontouchmove = null;

            var curRingWidth = $("#coolcontainer").width();
            var curRingHeight = $("#coolcontainer").height();
            
            $("#" + selectedItem).css("-webkit-filter", "");
            $("#" + selectedItem).css("filter", "");

            $(".innerpie").css("-webkit-filter", "");
            $(".innerpie").css("filter", "");
            $(".innerpie").css({zIndex: ''});
            
            if (currentRotation < 1 && currentRotation > -1) {
                let name =  $("#" + selectedItem).attr("alt");
                console.log("clicked item:", name);

                let box = "OUTER"
                let pie = name

                document.getElementById("audioFile").src = "/audio-files/" + box + "-" + pie + ".wav";

                console.log("Box " + box + " Pie:" + pie);

                let boxInfo = getText(box, pie);
                console.log(boxInfo);
                let title = boxInfo[0];
                let text = boxInfo[1];

                modal.setContent(`
                <h1>${title}</h1>
                <p>${text}</p>
                `);
                modal.open();

                
                $(".outerring").css("-webkit-transform","rotate(0deg)");
                currentRotation = 0;
                return;
            }

            var currentFingerPoint = ringCenterPoints[selectedItem];
            console.log("currentFingerPoint", currentFingerPoint);

            console.log(curRingWidth, curRingHeight)

            var remappedFingerX = remap(currentFingerPoint[0], 0, 2224, 0, curRingWidth);
            var remappedFingerY = remap(currentFingerPoint[1], 0, 2224, 0, curRingHeight);

            var remappedFingerXY = [remappedFingerX, remappedFingerY];

            console.log("post remap", remappedFingerXY);

            remappedFingerXY = rotatePoint(remappedFingerXY, [curRingWidth / 2, curRingHeight / 2], currentRotation);

            var midInterp = interpolate(remappedFingerXY, [curRingWidth / 2, curRingHeight / 2], 0.6);

            //var forwardRotatedXY = rotatePoint(midInterp, [curRingWidth / 2, curRingHeight / 2], currentRotation);
            var forwardRotatedXY = [0, 0];
            forwardRotatedXY[0] = remap(midInterp[0], 0, curRingWidth, 0, 2224);
            forwardRotatedXY[1] = remap(midInterp[1], 0, curRingHeight, 0, 2224);

            var targetItem = "";
            $.each(Object.keys(targetItemLocations), function(idx, key){
                if (pointIsInPoly(forwardRotatedXY, targetItemLocations[key])) {
                    targetItem = key;
                    return false;
                }
            });

            if (targetItem == "") {
                return;
            }


            let box = $.trim($("#" + selectedItem).attr("alt"));
            let pie =  $.trim($("#" + targetItem).attr("alt"));
            
            document.getElementById("audioFile").src = "/audio-files/" + box + "-" + pie + ".wav";

            console.log("Box " + box + " Pie:" + pie);
            
            let boxInfo = getText(box, pie);
            console.log(boxInfo);
            let title = boxInfo[0];
            let text = boxInfo[1];

            modal.setContent(`
            <h1>${title}</h1>
            <p>${text}</p>
            `); 
        
            modal.open();
            
            $(".outerring").css("-webkit-transform","rotate(0deg)");
            currentRotation = 0;
        }
    });
})

$(document).ready(function() {
    $("#helpbtn").click(function(){
        let boxInfo = getText("MISC", "HELP")

        let title = boxInfo[0]
        let text = boxInfo[1]

        document.getElementById("audioFile").src = "/audio-files/" + "MISC" + "-" + "HELP" + ".wav";

        modal.setContent(`
        <h1>${title}</h1>
        <p>${text}</p>
        `); 

        modal.open();
    
    }); 

    var defaultSliderValue = 15;
    $( "#volume-vertical" ).slider({
        orientation: "vertical",
        range: "min",
        min: 0,
        max: 100,
        value: defaultSliderValue,
        slide: function( event, ui ) {
            $( "#volumespan" ).text( ui.value );
            
            $('#audioFile').get(0).volume = (ui.value) / 100; 
        
            console.log(  $('#audioFile').get(0).volume)
        }
    });
    $( "#volumespan" ).text( defaultSliderValue );

    $("#volumetoggle").click(function(){
        $("#volumecontainer").slideToggle();
    })

});
