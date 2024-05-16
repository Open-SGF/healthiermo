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

$(document).ready(function() {

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

    $("#coolcontainer").on("mousedown touchstart", function(event) {
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
        var newY = remap(y, 0, curRingHeight, 0, 2228);

        console.log("mousedown pre remap", x, y);
        console.log("mousedown post remap", newX, newY);

        var selectedItem = "";
        $.each(Object.keys(draggableItemLocations), function(idx, key){
            if (pointIsInPoly([newX, newY], draggableItemLocations[key])) {
                selectedItem = key;
                return false;
            }
        });

        console.log("selectedItem", selectedItem);
        if (selectedItem == "")
            return

        var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
        if (event.type == "mousedown") {
            pos3 = event.clientX;
            pos4 = event.clientY;
            document.onmouseup = closeDragElement;
            document.onmousemove = elementDrag;
        } else {
            pos3 = event.targetTouches[0].clientX;
            pos4 = event.targetTouches[0].clientY;
            document.ontouchend = closeDragElement;
            document.ontouchmove = elementDrag;
        }

        function elementDrag(e) {
            e = e || window.event;

            // calculate the new cursor position:
            if (e.type == "touchmove") {
                pos1 = pos3 - e.targetTouches[0].clientX;
                pos2 = pos4 - e.targetTouches[0].clientY;
                pos3 = e.targetTouches[0].clientX;
                pos4 = e.targetTouches[0].clientY;
            } else {
                e.preventDefault();
                pos1 = pos3 - e.clientX;
                pos2 = pos4 - e.clientY;
                pos3 = e.clientX;
                pos4 = e.clientY;
            }

            var curPos = $("#" + selectedItem).offset();
            $("#" + selectedItem).offset({
                top: (curPos.top - pos2),
                left: (curPos.left - pos1)
            });
        }

        function closeDragElement(e) {
            // stop moving when mouse button is released:
            document.onmouseup = null;
            document.onmousemove = null;

            var endX = e.pageX - $('#coolcontainer').offset().left;
            var endY = e.pageY - $('#coolcontainer').offset().top;

            if (e.type == "touchend") {
                endX = e.changedTouches[0].pageX - $('#coolcontainer').offset().left;
                endY = e.changedTouches[0].pageY - $('#coolcontainer').offset().top;
            }

            $("#" + selectedItem).offset({
                top: initialPositions[selectedItem][0],
                left: initialPositions[selectedItem][1]
            });

            if (endX < 0 || endY > curRingWidth) {
                return;
            }

            if (endY < 0 || endY > curRingHeight) {
                return;
            }

            var curRingWidth = $("#coolcontainer").width();
            var curRingHeight = $("#coolcontainer").height();

            var newEndX = remap(endX, 0, curRingWidth, 0, 2224);
            var newEndY = remap(endY, 0, curRingHeight, 0, 2228);

            var targetItem = "";
            $.each(Object.keys(targetItemLocations), function(idx, key){
                if (pointIsInPoly([newEndX, newEndY], targetItemLocations[key])) {
                    targetItem = key;
                    return false;
                }
            });

            if (targetItem == "") {
                return;
            }

            console.log("target item:", targetItem);

            alert("you dragged " + selectedItem + " on to " + targetItem);
        }
    });
})