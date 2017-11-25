/**
 * Created by Administrator on 2017/2/14.
 */

/**
 * 
 * Base64 encode / decode 取自网上
 */

window.Base64 = function () {
    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}



// httpclient
window.getCookie = function (name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)"); // 正则匹配
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    }
    else {
        return null;
    }
}

function getUrlParamValue(paramName) {
    var url = decodeURIComponent(location.href);
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {};
    var i, j;
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
            .indexOf("=")
            + 1, j.length);
    }
    var returnValue = paraObj[paramName.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
};



function Tree(data, prop) {
    this.tree = data || []; // 数据

    this.groups = {}; // 分组
    this.pId = prop.pId ? prop.pId : 'pLabel';
    this.id = prop.id ? prop.id : 'id';
    this.rootId = prop.rootId ? prop.rootId : '0';
    this.children = prop.children ? prop.children : 'children';
    for (var key in this.tree) {
        var item = this.tree[key]
        if (!item[this.pId]) {
            item[this.pId] = 0;
        }
    }
}

// 将数据库查询返回的转成树状
Tree.prototype = {
    init: function (pid) {
        this.group();
        var data = this.getData(this.groups[pid]);
        return data;
    },
    group: function () {
        for (var i = 0; i < this.tree.length; i++) {
            if (!this.tree[i][this.pId]) {
                this.tree[i][this.pId] = this.rootId
            }
            if (this.groups[this.tree[i][this.pId]]) {
                this.groups[this.tree[i][this.pId]].push(this.tree[i]);
            } else {
                this.groups[this.tree[i][this.pId]] = [];
                this.groups[this.tree[i][this.pId]].push(this.tree[i]);
            }
        }
    },
    getData: function (info) {
        if (!info) return;

        var children = [];
        for (var i = 0; i < info.length; i++) {
            var item = info[i];
            var childrenGroup = this.groups[item[this.id]];
            if (childrenGroup) {
                var childItems = this.getData(this.groups[item[this.id]]);
                if (childItems.length > 0) {
                    if (!item.children) {
                        item.children = []
                    }
                    item.children = item[this.children].concat(childItems);
                }
            }
            children.push(item);
        }

        return children;
    },
    // 改成 ruleEngine可以识别的
    changeToRule: function () {
        var conditions = this.init(0);
        conditions = this.updateRuleChild(conditions);
        if (conditions.length == 1) {
            return conditions[0]
        }
    },
    updateRuleChild: function (conditions) {
        var ret = []
        for (var key in conditions) {
            var item = conditions[key];
            var newObj = {};
            if (item.children && item.children.length > 0) {
                newObj[item.operator] = this.updateRuleChild(item.children);
            } else {
                newObj = item;
            }
            ret.push(newObj);
        }
        return ret;
    }
}

function PromiseAll(willInvokeCount, callBack) {
    this.callBackTime = 0;
    this.willInvokeCount = willInvokeCount;
    this.callBack = callBack;
    this.callParams = {}
}


PromiseAll.prototype = {
    init: function () {
        return this;
    },
    PromiseAll: function () {
        var that = this;
        var callBackInner = function (arguments) {
            that.callBackTime++;
            that.callParams[that.callBackTime] = arguments;
            if (that.callBackTime == that.willInvokeCount) {
                // 全部返回
                that.callBack(that.callParams);
            }
        }
        return callBackInner;
    }
}


/*******************************************************************************
 * 字符串操作工具类 * 注：调用方式，strUtil.方法名 *
 ******************************************************************************/
var strUtil = {
    /*
	 * 判断字符串是否为空 @param str 传入的字符串 @returns {}
	 */
    isEmpty: function (str) {
        if (str) {
            return true;
        } else {
            return false;
        }
    },
    /*
	 * 判断两个字符串子否相同 @param str1 @param str2 @returns {Boolean}
	 */
    isEquals: function (str1, str2) {
        if (str1 == str2) {
            return true;
        } else {
            return false;
        }
    },
    /*
	 * 忽略大小写判断字符串是否相同 @param str1 @param str2 @returns {Boolean}
	 */
    isEqualsIgnorecase: function (str1, str2) {
        if (str1.toUpperCase() == str2.toUpperCase()) {
            return true;
        } else {
            return false;
        }
    },
    /**
	 * 判断输入是否是一个整数
	 * 
	 * @param value
	 * @returns {Boolean}
	 */
    isint:function(str) {
        var result = str.match(/^(-|\+)?\d+$/);
        if (result == null) return false;
        return true;
    },
    /**
	 * 判断是否是中文
	 * 
	 * @param str
	 * @returns {Boolean}
	 */
    isChine: function (str) {
        var reg = /^([u4E00-u9FA5]|[uFE30-uFFA0])*$/;
        if (reg.test(str)) {
            return false;
        }
        return true;
    }
};

/*******************************************************************************
 * 日期时间工具类 * 注：调用方式，deteUtil.方法名 *
 ******************************************************************************/  
var dateUtil = {  
    /*
	 * 方法作用：【取传入日期是星期几】 使用方法：dateUtil.nowFewWeeks(new Date()); @param date{date}
	 * 传入日期类型 @returns {星期四，...}
	 */  
    nowFewWeeks:function(date){  
        if(date instanceof Date){  
            var dayNames = new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");  
            return dayNames[date.getDay()];  
        } else{  
            return "Param error,date type!";  
        }  
    },  
    /*
	 * 方法作用：【字符串转换成日期】 使用方法：dateUtil.strTurnDate("2010-01-01"); @param str
	 * {String}字符串格式的日期，传入格式：yyyy-mm-dd(2015-01-31) @return {Date}由字符串转换成的日期
	 */  
    strTurnDate:function(str){  
        var   re   =   /^(\d{4})\S(\d{1,2})\S(\d{1,2})$/;  
        var   dt;  
        if   (re.test(str)){  
            dt   =   new   Date(RegExp.$1,RegExp.$2   -   1,RegExp.$3);  
        }  
        return dt;  
    },  
    /*
	 * 方法作用：【计算2个日期之间的天数】 传入格式：yyyy-mm-dd(2015-01-31)
	 * 使用方法：dateUtil.dayMinus(startDate,endDate); @startDate {Date}起始日期 @endDate
	 * {Date}结束日期 @return endDate - startDate的天数差
	 */  
    dayMinus:function(startDate, endDate){  
        if(startDate instanceof Date && endDate instanceof Date){  
            var days = Math.floor((endDate-startDate)/(1000 * 60 * 60 * 24));  
            return days;  
        }else{  
            return "Param error,date type!";  
        }  
    }  
};  

