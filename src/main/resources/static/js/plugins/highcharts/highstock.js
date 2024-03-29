/*
 Highstock JS v8.0.4 (2020-03-10)

 (c) 2009-2018 Torstein Honsi

 License: www.highcharts.com/license
*/
(function(U, P) {
    "object" === typeof module && module.exports ? (P["default"] = P, module.exports = U.document ? P(U) : P) : "function" === typeof define && define.amd ? define("highcharts/highstock",
    function() {
        return P(U)
    }) : (U.Highcharts && U.Highcharts.error(16, !0), U.Highcharts = P(U))
})("undefined" !== typeof window ? window: this,
function(U) {
    function P(f, m, J, r) {
        f.hasOwnProperty(m) || (f[m] = r.apply(null, J))
    }
    var y = {};
    P(y, "parts/Globals.js", [],
    function() {
        var f = "undefined" !== typeof U ? U: "undefined" !== typeof window ? window: {},
        m = f.document,
        J = f.navigator && f.navigator.userAgent || "",
        r = m && m.createElementNS && !!m.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect,
        E = /(edge|msie|trident)/i.test(J) && !f.opera,
        N = -1 !== J.indexOf("Firefox"),
        F = -1 !== J.indexOf("Chrome"),
        C = N && 4 > parseInt(J.split("Firefox/")[1], 10);
        return {
            product: "Highcharts",
            version: "8.0.4",
            deg2rad: 2 * Math.PI / 360,
            doc: m,
            hasBidiBug: C,
            hasTouch: !!f.TouchEvent,
            isMS: E,
            isWebKit: -1 !== J.indexOf("AppleWebKit"),
            isFirefox: N,
            isChrome: F,
            isSafari: !F && -1 !== J.indexOf("Safari"),
            isTouchDevice: /(Mobile|Android|Windows Phone)/.test(J),
            SVG_NS: "http://www.w3.org/2000/svg",
            chartCount: 0,
            seriesTypes: {},
            symbolSizes: {},
            svg: r,
            win: f,
            marginNames: ["plotTop", "marginRight", "marginBottom", "plotLeft"],
            noop: function() {},
            charts: [],
            dateFormats: {}
        }
    });
    P(y, "parts/Utilities.js", [y["parts/Globals.js"]],
    function(f) {
        function m() {
            var d, a = arguments,
            b = {},
            c = function(d, a) {
                "object" !== typeof d && (d = {});
                Y(a,
                function(b, h) { ! t(b, !0) || k(b) || n(b) ? d[h] = a[h] : d[h] = c(d[h] || {},
                    b)
                });
                return d
            }; ! 0 === a[0] && (b = a[1], a = Array.prototype.slice.call(a, 2));
            var h = a.length;
            for (d = 0; d < h; d++) b = c(b, a[d]);
            return b
        }
        function J(d, a, b) {
            var h;
            x(a) ? c(b) ? d.setAttribute(a, b) : d && d.getAttribute && ((h = d.getAttribute(a)) || "class" !== a || (h = d.getAttribute(a + "Name"))) : Y(a,
            function(a, b) {
                d.setAttribute(b, a)
            });
            return h
        }
        function r() {
            for (var d = arguments,
            a = d.length,
            b = 0; b < a; b++) {
                var c = d[b];
                if ("undefined" !== typeof c && null !== c) return c
            }
        }
        function E(d, a) {
            if (!d) return a;
            var b = d.split(".").reverse();
            if (1 === b.length) return a[d];
            for (d = b.pop();
            "undefined" !== typeof d && "undefined" !== typeof a && null !== a;) a = a[d],
            d = b.pop();
            return a
        }
        f.timers = [];
        var N = f.charts,
        F = f.doc,
        C = f.win,
        B = f.error = function(d, a, b, c) {
            var h = q(d),
            e = h ? "Highcharts error #" + d + ": www.highcharts.com/errors/" + d + "/": d.toString(),
            K = function() {
                if (a) throw Error(e);
                C.console && console.log(e)
            };
            if ("undefined" !== typeof c) {
                var l = "";
                h && (e += "?");
                f.objectEach(c,
                function(d, a) {
                    l += "\n" + a + ": " + d;
                    h && (e += encodeURI(a) + "=" + encodeURI(d))
                });
                e += l
            }
            b ? f.fireEvent(b, "displayError", {
                code: d,
                message: e,
                params: c
            },
            K) : K()
        },
        L = function() {
            function d(d, a, b) {
                this.options = a;
                this.elem = d;
                this.prop = b
            }
            d.prototype.dSetter = function() {
                var d = this.paths[0],
                a = this.paths[1],
                b = [],
                c = this.now,
                h = d.length;
                if (1 === c) b = this.toD;
                else if (h === a.length && 1 > c) for (; h--;) {
                    var e = parseFloat(d[h]);
                    b[h] = isNaN(e) || "A" === a[h - 4] || "A" === a[h - 5] ? a[h] : c * parseFloat("" + (a[h] - e)) + e
                } else b = a;
                this.elem.attr("d", b, null, !0)
            };
            d.prototype.update = function() {
                var d = this.elem,
                a = this.prop,
                b = this.now,
                c = this.options.step;
                if (this[a + "Setter"]) this[a + "Setter"]();
                else d.attr ? d.element && d.attr(a, b, null, !0) : d.style[a] = b + this.unit;
                c && c.call(d, b, this)
            };
            d.prototype.run = function(d, a, b) {
                var c = this,
                h = c.options,
                e = function(d) {
                    return e.stopped ? !1 : c.step(d)
                },
                K = C.requestAnimationFrame ||
                function(d) {
                    setTimeout(d, 13)
                },
                l = function() {
                    for (var d = 0; d < f.timers.length; d++) f.timers[d]() || f.timers.splice(d--, 1);
                    f.timers.length && K(l)
                };
                d !== a || this.elem["forceAnimate:" + this.prop] ? (this.startTime = +new Date, this.start = d, this.end = a, this.unit = b, this.now = this.start, this.pos = 0, e.elem = this.elem, e.prop = this.prop, e() && 1 === f.timers.push(e) && K(l)) : (delete h.curAnim[this.prop], h.complete && 0 === Object.keys(h.curAnim).length && h.complete.call(this.elem))
            };
            d.prototype.step = function(d) {
                var a = +new Date,
                b = this.options,
                c = this.elem,
                h = b.complete,
                e = b.duration,
                K = b.curAnim;
                if (c.attr && !c.element) d = !1;
                else if (d || a >= e + this.startTime) {
                    this.now = this.end;
                    this.pos = 1;
                    this.update();
                    var l = K[this.prop] = !0;
                    Y(K,
                    function(d) { ! 0 !== d && (l = !1)
                    });
                    l && h && h.call(c);
                    d = !1
                } else this.pos = b.easing((a - this.startTime) / e),
                this.now = this.start + (this.end - this.start) * this.pos,
                this.update(),
                d = !0;
                return d
            };
            d.prototype.initPath = function(d, a, b) {
                function c(d) {
                    for (p = d.length; p--;) {
                        var a = "M" === d[p] || "L" === d[p];
                        var b = /[a-zA-Z]/.test(d[p + 3]);
                        a && b && d.splice(p + 1, 0, d[p + 1], d[p + 2], d[p + 1], d[p + 2])
                    }
                }
                function h(d, a) {
                    for (; d.length < k;) {
                        d[0] = a[k - d.length];
                        var b = d.slice(0, G); [].splice.apply(d, [0, 0].concat(b));
                        O && (b = d.slice(d.length - G), [].splice.apply(d, [d.length, 0].concat(b)), p--)
                    }
                    d[0] = "M"
                }
                function e(d, a) {
                    for (var b = (k - d.length) / G; 0 < b && b--;) I = d.slice().splice(d.length / v - G, G * v),
                    I[0] = a[k - G - b * G],
                    g && (I[G - 6] = I[G - 2], I[G - 5] = I[G - 1]),
                    [].splice.apply(d, [d.length / v, 0].concat(I)),
                    O && b--
                }
                a = a || "";
                var K = d.startX,
                l = d.endX,
                g = -1 < a.indexOf("C"),
                G = g ? 7 : 3,
                I,
                p;
                a = a.split(" ");
                b = b.slice();
                var O = d.isArea,
                v = O ? 2 : 1;
                g && (c(a), c(b));
                if (K && l) {
                    for (p = 0; p < K.length; p++) if (K[p] === l[0]) {
                        var u = p;
                        break
                    } else if (K[0] === l[l.length - K.length + p]) {
                        u = p;
                        var S = !0;
                        break
                    } else if (K[K.length - 1] === l[l.length - K.length + p]) {
                        u = K.length - p;
                        break
                    }
                    "undefined" === typeof u && (a = [])
                }
                if (a.length && q(u)) {
                    var k = b.length + u * v * G;
                    S ? (h(a, b), e(b, a)) : (h(b, a), e(a, b))
                }
                return [a, b]
            };
            d.prototype.fillSetter = function() {
                f.Fx.prototype.strokeSetter.apply(this, arguments)
            };
            d.prototype.strokeSetter = function() {
                this.elem.attr(this.prop, f.color(this.start).tweenTo(f.color(this.end), this.pos), null, !0)
            };
            return d
        } ();
        f.Fx = L;
        f.merge = m;
        var z = f.pInt = function(d, a) {
            return parseInt(d, a || 10)
        },
        x = f.isString = function(d) {
            return "string" === typeof d
        },
        A = f.isArray = function(d) {
            d = Object.prototype.toString.call(d);
            return "[object Array]" === d || "[object Array Iterator]" === d
        },
        t = f.isObject = function(d, a) {
            return !! d && "object" === typeof d && (!a || !A(d))
        },
        n = f.isDOMElement = function(d) {
            return t(d) && "number" === typeof d.nodeType
        },
        k = f.isClass = function(d) {
            var a = d && d.constructor;
            return ! (!t(d, !0) || n(d) || !a || !a.name || "Object" === a.name)
        },
        q = f.isNumber = function(d) {
            return "number" === typeof d && !isNaN(d) && Infinity > d && -Infinity < d
        },
        e = f.erase = function(d, a) {
            for (var b = d.length; b--;) if (d[b] === a) {
                d.splice(b, 1);
                break
            }
        },
        c = f.defined = function(d) {
            return "undefined" !== typeof d && null !== d
        };
        f.attr = J;
        var g = f.splat = function(d) {
            return A(d) ? d: [d]
        },
        p = f.syncTimeout = function(d, a, b) {
            if (0 < a) return setTimeout(d, a, b);
            d.call(0, b);
            return - 1
        },
        b = f.clearTimeout = function(d) {
            c(d) && clearTimeout(d)
        },
        a = f.extend = function(d, a) {
            var b;
            d || (d = {});
            for (b in a) d[b] = a[b];
            return d
        };
        f.pick = r;
        var v = f.css = function(d, b) {
            f.isMS && !f.svg && b && "undefined" !== typeof b.opacity && (b.filter = "alpha(opacity=" + 100 * b.opacity + ")");
            a(d.style, b)
        },
        D = f.createElement = function(d, b, c, h, e) {
            d = F.createElement(d);
            b && a(d, b);
            e && v(d, {
                padding: "0",
                border: "none",
                margin: "0"
            });
            c && v(d, c);
            h && h.appendChild(d);
            return d
        },
        w = f.extendClass = function(d, b) {
            var c = function() {};
            c.prototype = new d;
            a(c.prototype, b);
            return c
        },
        l = f.pad = function(d, a, b) {
            return Array((a || 2) + 1 - String(d).replace("-", "").length).join(b || "0") + d
        },
        h = f.relativeLength = function(d, a, b) {
            return /%$/.test(d) ? a * parseFloat(d) / 100 + (b || 0) : parseFloat(d)
        },
        u = f.wrap = function(d, a, b) {
            var c = d[a];
            d[a] = function() {
                var d = Array.prototype.slice.call(arguments),
                a = arguments,
                h = this;
                h.proceed = function() {
                    c.apply(h, arguments.length ? arguments: a)
                };
                d.unshift(c);
                d = b.apply(this, d);
                h.proceed = null;
                return d
            }
        },
        H = f.format = function(d, a, b) {
            var c = "{",
            h = !1,
            e = [],
            K = /f$/,
            l = /\.([0-9])/,
            g = f.defaultOptions.lang,
            G = b && b.time || f.time;
            for (b = b && b.numberFormatter || X; d;) {
                var p = d.indexOf(c);
                if ( - 1 === p) break;
                var I = d.slice(0, p);
                if (h) {
                    I = I.split(":");
                    c = E(I.shift() || "", a);
                    if (I.length && "number" === typeof c) if (I = I.join(":"), K.test(I)) {
                        var O = parseInt((I.match(l) || ["", "-1"])[1], 10);
                        null !== c && (c = b(c, O, g.decimalPoint, -1 < I.indexOf(",") ? g.thousandsSep: ""))
                    } else c = G.dateFormat(I, c);
                    e.push(c)
                } else e.push(I);
                d = d.slice(p + 1);
                c = (h = !h) ? "}": "{"
            }
            e.push(d);
            return e.join("")
        },
        M = f.getMagnitude = function(d) {
            return Math.pow(10, Math.floor(Math.log(d) / Math.LN10))
        },
        Q = f.normalizeTickInterval = function(d, a, b, c, h) {
            var e = d;
            b = r(b, 1);
            var K = d / b;
            a || (a = h ? [1, 1.2, 1.5, 2, 2.5, 3, 4, 5, 6, 8, 10] : [1, 2, 2.5, 5, 10], !1 === c && (1 === b ? a = a.filter(function(d) {
                return 0 === d % 1
            }) : .1 >= b && (a = [1 / b])));
            for (c = 0; c < a.length && !(e = a[c], h && e * b >= d || !h && K <= (a[c] + (a[c + 1] || a[c])) / 2); c++);
            return e = V(e * b, -Math.round(Math.log(.001) / Math.LN10))
        },
        G = f.stableSort = function(d, a) {
            var b = d.length,
            c, h;
            for (h = 0; h < b; h++) d[h].safeI = h;
            d.sort(function(d, b) {
                c = a(d, b);
                return 0 === c ? d.safeI - b.safeI: c
            });
            for (h = 0; h < b; h++) delete d[h].safeI
        },
        d = f.arrayMin = function(d) {
            for (var a = d.length,
            b = d[0]; a--;) d[a] < b && (b = d[a]);
            return b
        },
        I = f.arrayMax = function(d) {
            for (var a = d.length,
            b = d[0]; a--;) d[a] > b && (b = d[a]);
            return b
        },
        O = f.destroyObjectProperties = function(d, a) {
            Y(d,
            function(b, c) {
                b && b !== a && b.destroy && b.destroy();
                delete d[c]
            })
        },
        T = f.discardElement = function(d) {
            var a = f.garbageBin;
            a || (a = D("div"));
            d && a.appendChild(d);
            a.innerHTML = ""
        },
        V = f.correctFloat = function(d, a) {
            return parseFloat(d.toPrecision(a || 14))
        },
        W = f.setAnimation = function(d, a) {
            a.renderer.globalAnimation = r(d, a.options.chart.animation, !0)
        },
        R = f.animObject = function(d) {
            return t(d) ? m(d) : {
                duration: d ? 500 : 0
            }
        },
        K = f.timeUnits = {
            millisecond: 1,
            second: 1E3,
            minute: 6E4,
            hour: 36E5,
            day: 864E5,
            week: 6048E5,
            month: 24192E5,
            year: 314496E5
        },
        X = f.numberFormat = function(d, a, b, c) {
            d = +d || 0;
            a = +a;
            var h = f.defaultOptions.lang,
            e = (d.toString().split(".")[1] || "").split("e")[0].length,
            K = d.toString().split("e");
            if ( - 1 === a) a = Math.min(e, 20);
            else if (!q(a)) a = 2;
            else if (a && K[1] && 0 > K[1]) {
                var l = a + +K[1];
                0 <= l ? (K[0] = ( + K[0]).toExponential(l).split("e")[0], a = l) : (K[0] = K[0].split(".")[0] || 0, d = 20 > a ? (K[0] * Math.pow(10, K[1])).toFixed(a) : 0, K[1] = 0)
            }
            var g = (Math.abs(K[1] ? K[0] : d) + Math.pow(10, -Math.max(a, e) - 1)).toFixed(a);
            e = String(z(g));
            l = 3 < e.length ? e.length % 3 : 0;
            b = r(b, h.decimalPoint);
            c = r(c, h.thousandsSep);
            d = (0 > d ? "-": "") + (l ? e.substr(0, l) + c: "");
            d += e.substr(l).replace(/(\d{3})(?=\d)/g, "$1" + c);
            a && (d += b + g.slice( - a));
            K[1] && 0 !== +d && (d += "e" + K[1]);
            return d
        };
        Math.easeInOutSine = function(d) {
            return - .5 * (Math.cos(Math.PI * d) - 1)
        };
        var ba = f.getStyle = function(d, a, b) {
            if ("width" === a) return a = Math.min(d.offsetWidth, d.scrollWidth),
            b = d.getBoundingClientRect && d.getBoundingClientRect().width,
            b < a && b >= a - 1 && (a = Math.floor(b)),
            Math.max(0, a - f.getStyle(d, "padding-left") - f.getStyle(d, "padding-right"));
            if ("height" === a) return Math.max(0, Math.min(d.offsetHeight, d.scrollHeight) - f.getStyle(d, "padding-top") - f.getStyle(d, "padding-bottom"));
            C.getComputedStyle || B(27, !0);
            if (d = C.getComputedStyle(d, void 0)) d = d.getPropertyValue(a),
            r(b, "opacity" !== a) && (d = z(d));
            return d
        },
        Z = f.inArray = function(d, a, b) {
            return a.indexOf(d, b)
        },
        S = f.find = Array.prototype.find ?
        function(d, a) {
            return d.find(a)
        }: function(d, a) {
            var b, c = d.length;
            for (b = 0; b < c; b++) if (a(d[b], b)) return d[b]
        };
        f.keys = Object.keys;
        var aa = f.offset = function(d) {
            var a = F.documentElement;
            d = d.parentElement || d.parentNode ? d.getBoundingClientRect() : {
                top: 0,
                left: 0
            };
            return {
                top: d.top + (C.pageYOffset || a.scrollTop) - (a.clientTop || 0),
                left: d.left + (C.pageXOffset || a.scrollLeft) - (a.clientLeft || 0)
            }
        },
        ca = f.stop = function(d, a) {
            for (var b = f.timers.length; b--;) f.timers[b].elem !== d || a && a !== f.timers[b].prop || (f.timers[b].stopped = !0)
        },
        Y = f.objectEach = function(d, a, b) {
            for (var c in d) Object.hasOwnProperty.call(d, c) && a.call(b || d[c], d[c], c, d)
        };
        Y({
            map: "map",
            each: "forEach",
            grep: "filter",
            reduce: "reduce",
            some: "some"
        },
        function(d, a) {
            f[a] = function(a) {
                return Array.prototype[d].apply(a, [].slice.call(arguments, 1))
            }
        });
        var fa = f.addEvent = function(d, a, b, c) {
            void 0 === c && (c = {});
            var h = d.addEventListener || f.addEventListenerPolyfill;
            var e = "function" === typeof d && d.prototype ? d.prototype.protoEvents = d.prototype.protoEvents || {}: d.hcEvents = d.hcEvents || {};
            f.Point && d instanceof f.Point && d.series && d.series.chart && (d.series.chart.runTrackerClick = !0);
            h && h.call(d, a, b, !1);
            e[a] || (e[a] = []);
            e[a].push({
                fn: b,
                order: "number" === typeof c.order ? c.order: Infinity
            });
            e[a].sort(function(d, a) {
                return d.order - a.order
            });
            return function() {
                da(d, a, b)
            }
        },
        da = f.removeEvent = function(d, a, b) {
            function c(a, b) {
                var c = d.removeEventListener || f.removeEventListenerPolyfill;
                c && c.call(d, a, b, !1)
            }
            function h(b) {
                var h;
                if (d.nodeName) {
                    if (a) {
                        var e = {};
                        e[a] = !0
                    } else e = b;
                    Y(e,
                    function(d, a) {
                        if (b[a]) for (h = b[a].length; h--;) c(a, b[a][h].fn)
                    })
                }
            }
            var e; ["protoEvents", "hcEvents"].forEach(function(K, l) {
                var g = (l = l ? d: d.prototype) && l[K];
                g && (a ? (e = g[a] || [], b ? (g[a] = e.filter(function(d) {
                    return b !== d.fn
                }), c(a, b)) : (h(g), g[a] = [])) : (h(g), l[K] = {}))
            })
        },
        ha = f.fireEvent = function(d, b, c, h) {
            var e;
            c = c || {};
            if (F.createEvent && (d.dispatchEvent || d.fireEvent)) {
                var K = F.createEvent("Events");
                K.initEvent(b, !0, !0);
                a(K, c);
                d.dispatchEvent ? d.dispatchEvent(K) : d.fireEvent(b, K)
            } else c.target || a(c, {
                preventDefault: function() {
                    c.defaultPrevented = !0
                },
                target: d,
                type: b
            }),
            function(a, b) {
                void 0 === a && (a = []);
                void 0 === b && (b = []);
                var h = 0,
                K = 0,
                l = a.length + b.length;
                for (e = 0; e < l; e++) ! 1 === (a[h] ? b[K] ? a[h].order <= b[K].order ? a[h++] : b[K++] : a[h++] : b[K++]).fn.call(d, c) && c.preventDefault()
            } (d.protoEvents && d.protoEvents[b], d.hcEvents && d.hcEvents[b]);
            h && !c.defaultPrevented && h.call(d, c)
        },
        ia = f.animate = function(d, a, b) {
            var c, h = "",
            e, K;
            if (!t(b)) {
                var l = arguments;
                b = {
                    duration: l[2],
                    easing: l[3],
                    complete: l[4]
                }
            }
            q(b.duration) || (b.duration = 400);
            b.easing = "function" === typeof b.easing ? b.easing: Math[b.easing] || Math.easeInOutSine;
            b.curAnim = m(a);
            Y(a,
            function(l, g) {
                ca(d, g);
                K = new L(d, b, g);
                e = null;
                "d" === g ? (K.paths = K.initPath(d, d.d, a.d), K.toD = a.d, c = 0, e = 1) : d.attr ? c = d.attr(g) : (c = parseFloat(ba(d, g)) || 0, "opacity" !== g && (h = "px"));
                e || (e = l);
                e && e.match && e.match("px") && (e = e.replace(/px/g, ""));
                K.run(c, e, h)
            })
        },
        y = f.seriesType = function(d, a, b, c, h) {
            var e = f.getOptions(),
            K = f.seriesTypes;
            e.plotOptions[d] = m(e.plotOptions[a], b);
            K[d] = w(K[a] ||
            function() {},
            c);
            K[d].prototype.type = d;
            h && (K[d].prototype.pointClass = w(f.Point, h));
            return K[d]
        },
        ea = f.uniqueKey = function() {
            var d = Math.random().toString(36).substring(2, 9),
            a = 0;
            return function() {
                return "highcharts-" + d + "-" + a++
            }
        } (),
        ja = f.isFunction = function(d) {
            return "function" === typeof d
        };
        C.jQuery && (C.jQuery.fn.highcharts = function() {
            var d = [].slice.call(arguments);
            if (this[0]) return d[0] ? (new(f[x(d[0]) ? d.shift() : "Chart"])(this[0], d[0], d[1]), this) : N[J(this[0], "data-highcharts-chart")]
        });
        return {
            Fx: L,
            addEvent: fa,
            animate: ia,
            animObject: R,
            arrayMax: I,
            arrayMin: d,
            attr: J,
            clamp: function(d, a, b) {
                return d > a ? d < b ? d: b: a
            },
            clearTimeout: b,
            correctFloat: V,
            createElement: D,
            css: v,
            defined: c,
            destroyObjectProperties: O,
            discardElement: T,
            erase: e,
            error: B,
            extend: a,
            extendClass: w,
            find: S,
            fireEvent: ha,
            format: H,
            getMagnitude: M,
            getNestedProperty: E,
            getStyle: ba,
            inArray: Z,
            isArray: A,
            isClass: k,
            isDOMElement: n,
            isFunction: ja,
            isNumber: q,
            isObject: t,
            isString: x,
            merge: m,
            normalizeTickInterval: Q,
            numberFormat: X,
            objectEach: Y,
            offset: aa,
            pad: l,
            pick: r,
            pInt: z,
            relativeLength: h,
            removeEvent: da,
            seriesType: y,
            setAnimation: W,
            splat: g,
            stableSort: G,
            stop: ca,
            syncTimeout: p,
            timeUnits: K,
            uniqueKey: ea,
            wrap: u
        }
    });
    P(y, "parts/Color.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.isNumber,
        r = m.merge,
        E = m.pInt;
        m = function() {
            function f(m) {
                this.parsers = [{
                    regex: /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/,
                    parse: function(f) {
                        return [E(f[1]), E(f[2]), E(f[3]), parseFloat(f[4], 10)]
                    }
                },
                {
                    regex: /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/,
                    parse: function(f) {
                        return [E(f[1]), E(f[2]), E(f[3]), 1]
                    }
                }];
                this.rgba = [];
                if (! (this instanceof f)) return new f(m);
                this.init(m)
            }
            f.parse = function(m) {
                return new f(m)
            };
            f.prototype.init = function(m) {
                var C, B;
                if ((this.input = m = f.names[m && m.toLowerCase ? m.toLowerCase() : ""] || m) && m.stops) this.stops = m.stops.map(function(x) {
                    return new f(x[1])
                });
                else {
                    if (m && m.charAt && "#" === m.charAt()) {
                        var r = m.length;
                        m = parseInt(m.substr(1), 16);
                        7 === r ? C = [(m & 16711680) >> 16, (m & 65280) >> 8, m & 255, 1] : 4 === r && (C = [(m & 3840) >> 4 | (m & 3840) >> 8, (m & 240) >> 4 | m & 240, (m & 15) << 4 | m & 15, 1])
                    }
                    if (!C) for (B = this.parsers.length; B--&&!C;) {
                        var z = this.parsers[B]; (r = z.regex.exec(m)) && (C = z.parse(r))
                    }
                }
                this.rgba = C || []
            };
            f.prototype.get = function(f) {
                var m = this.input,
                B = this.rgba;
                if ("undefined" !== typeof this.stops) {
                    var F = r(m);
                    F.stops = [].concat(F.stops);
                    this.stops.forEach(function(z, x) {
                        F.stops[x] = [F.stops[x][0], z.get(f)]
                    })
                } else F = B && J(B[0]) ? "rgb" === f || !f && 1 === B[3] ? "rgb(" + B[0] + "," + B[1] + "," + B[2] + ")": "a" === f ? B[3] : "rgba(" + B.join(",") + ")": m;
                return F
            };
            f.prototype.brighten = function(f) {
                var m, B = this.rgba;
                if (this.stops) this.stops.forEach(function(m) {
                    m.brighten(f)
                });
                else if (J(f) && 0 !== f) for (m = 0; 3 > m; m++) B[m] += E(255 * f),
                0 > B[m] && (B[m] = 0),
                255 < B[m] && (B[m] = 255);
                return this
            };
            f.prototype.setOpacity = function(f) {
                this.rgba[3] = f;
                return this
            };
            f.prototype.tweenTo = function(f, m) {
                var B = this.rgba,
                r = f.rgba;
                r.length && B && B.length ? (f = 1 !== r[3] || 1 !== B[3], m = (f ? "rgba(": "rgb(") + Math.round(r[0] + (B[0] - r[0]) * (1 - m)) + "," + Math.round(r[1] + (B[1] - r[1]) * (1 - m)) + "," + Math.round(r[2] + (B[2] - r[2]) * (1 - m)) + (f ? "," + (r[3] + (B[3] - r[3]) * (1 - m)) : "") + ")") : m = f.input || "none";
                return m
            };
            f.names = {
                white: "#ffffff",
                black: "#000000"
            };
            return f
        } ();
        f.Color = m;
        f.color = m.parse;
        return f.Color
    });
    P(y, "parts/SvgRenderer.js", [y["parts/Globals.js"], y["parts/Color.js"], y["parts/Utilities.js"]],
    function(f, m, J) {
        var r = m.parse,
        E = J.addEvent,
        N = J.animate,
        F = J.animObject,
        C = J.attr,
        B = J.createElement,
        L = J.css,
        z = J.defined,
        x = J.destroyObjectProperties,
        A = J.erase,
        t = J.extend,
        n = J.inArray,
        k = J.isArray,
        q = J.isNumber,
        e = J.isObject,
        c = J.isString,
        g = J.merge,
        p = J.objectEach,
        b = J.pick,
        a = J.pInt,
        v = J.removeEvent,
        D = J.splat,
        w = J.stop,
        l = J.uniqueKey,
        h = f.charts,
        u = f.deg2rad,
        H = f.doc,
        M = f.hasTouch,
        Q = f.isFirefox,
        G = f.isMS,
        d = f.isWebKit,
        I = f.noop,
        O = f.svg,
        T = f.SVG_NS,
        V = f.symbolSizes,
        W = f.win;
        var R = f.SVGElement = function() {
            return this
        };
        t(R.prototype, {
            opacity: 1,
            SVG_NS: T,
            textProps: "direction fontSize fontWeight fontFamily fontStyle color lineHeight width textAlign textDecoration textOverflow textOutline cursor".split(" "),
            init: function(d, a) {
                this.element = "span" === a ? B(a) : H.createElementNS(this.SVG_NS, a);
                this.renderer = d;
                f.fireEvent(this, "afterInit")
            },
            animate: function(d, a, c) {
                var h = F(b(a, this.renderer.globalAnimation, !0));
                b(H.hidden, H.msHidden, H.webkitHidden, !1) && (h.duration = 0);
                0 !== h.duration ? (c && (h.complete = c), N(this, d, h)) : (this.attr(d, void 0, c), p(d,
                function(d, a) {
                    h.step && h.step.call(this, d, {
                        prop: a,
                        pos: 1
                    })
                },
                this));
                return this
            },
            complexColor: function(d, a, b) {
                var c = this.renderer,
                h, e, K, G, I, O, v, u, w, X, D, H = [],
                n;
                f.fireEvent(this.renderer, "complexColor", {
                    args: arguments
                },
                function() {
                    d.radialGradient ? e = "radialGradient": d.linearGradient && (e = "linearGradient");
                    e && (K = d[e], I = c.gradients, v = d.stops, X = b.radialReference, k(K) && (d[e] = K = {
                        x1: K[0],
                        y1: K[1],
                        x2: K[2],
                        y2: K[3],
                        gradientUnits: "userSpaceOnUse"
                    }), "radialGradient" === e && X && !z(K.gradientUnits) && (G = K, K = g(K, c.getRadialAttr(X, G), {
                        gradientUnits: "userSpaceOnUse"
                    })), p(K,
                    function(d, a) {
                        "id" !== a && H.push(a, d)
                    }), p(v,
                    function(d) {
                        H.push(d)
                    }), H = H.join(","), I[H] ? D = I[H].attr("id") : (K.id = D = l(), I[H] = O = c.createElement(e).attr(K).add(c.defs), O.radAttr = G, O.stops = [], v.forEach(function(d) {
                        0 === d[1].indexOf("rgba") ? (h = r(d[1]), u = h.get("rgb"), w = h.get("a")) : (u = d[1], w = 1);
                        d = c.createElement("stop").attr({
                            offset: d[0],
                            "stop-color": u,
                            "stop-opacity": w
                        }).add(O);
                        O.stops.push(d)
                    })), n = "url(" + c.url + "#" + D + ")", b.setAttribute(a, n), b.gradient = H, d.toString = function() {
                        return n
                    })
                })
            },
            applyTextOutline: function(d) {
                var a = this.element,
                b; - 1 !== d.indexOf("contrast") && (d = d.replace(/contrast/g, this.renderer.getContrast(a.style.fill)));
                d = d.split(" ");
                var c = d[d.length - 1];
                if ((b = d[0]) && "none" !== b && f.svg) {
                    this.fakeTS = !0;
                    d = [].slice.call(a.getElementsByTagName("tspan"));
                    this.ySetter = this.xSetter;
                    b = b.replace(/(^[\d\.]+)(.*?)$/g,
                    function(d, a, b) {
                        return 2 * a + b
                    });
                    this.removeTextOutline(d);
                    var h = a.textContent ? /^[\u0591-\u065F\u066A-\u07FF\uFB1D-\uFDFD\uFE70-\uFEFC]/.test(a.textContent) : !1;
                    var e = a.firstChild;
                    d.forEach(function(d, K) {
                        0 === K && (d.setAttribute("x", a.getAttribute("x")), K = a.getAttribute("y"), d.setAttribute("y", K || 0), null === K && a.setAttribute("y", 0));
                        K = d.cloneNode(!0);
                        C(h && !Q ? d: K, {
                            "class": "highcharts-text-outline",
                            fill: c,
                            stroke: c,
                            "stroke-width": b,
                            "stroke-linejoin": "round"
                        });
                        a.insertBefore(K, e)
                    });
                    h && Q && d[0] && (d = d[0].cloneNode(!0), d.textContent = " ", a.insertBefore(d, e))
                }
            },
            removeTextOutline: function(d) {
                for (var a = d.length,
                b; a--;) b = d[a],
                "highcharts-text-outline" === b.getAttribute("class") && A(d, this.element.removeChild(b))
            },
            symbolCustomAttribs: "x y width height r start end innerR anchorX anchorY rounded".split(" "),
            attr: function(d, a, b, c) {
                var h = this.element,
                e, K = this,
                l, g, G = this.symbolCustomAttribs;
                if ("string" === typeof d && "undefined" !== typeof a) {
                    var I = d;
                    d = {};
                    d[I] = a
                }
                "string" === typeof d ? K = (this[d + "Getter"] || this._defaultGetter).call(this, d, h) : (p(d,
                function(a, b) {
                    l = !1;
                    c || w(this, b);
                    this.symbolName && -1 !== n(b, G) && (e || (this.symbolAttr(d), e = !0), l = !0); ! this.rotation || "x" !== b && "y" !== b || (this.doTransform = !0);
                    l || (g = this[b + "Setter"] || this._defaultSetter, g.call(this, a, b, h), !this.styledMode && this.shadows && /^(width|height|visibility|x|y|d|transform|cx|cy|r)$/.test(b) && this.updateShadows(b, a, g))
                },
                this), this.afterSetters());
                b && b.call(this);
                return K
            },
            afterSetters: function() {
                this.doTransform && (this.updateTransform(), this.doTransform = !1)
            },
            updateShadows: function(d, a, b) {
                for (var c = this.shadows,
                h = c.length; h--;) b.call(c[h], "height" === d ? Math.max(a - (c[h].cutHeight || 0), 0) : "d" === d ? this.d: a, d, c[h])
            },
            addClass: function(d, a) {
                var b = a ? "": this.attr("class") || "";
                d = (d || "").split(/ /g).reduce(function(d, a) { - 1 === b.indexOf(a) && d.push(a);
                    return d
                },
                b ? [b] : []).join(" ");
                d !== b && this.attr("class", d);
                return this
            },
            hasClass: function(d) {
                return - 1 !== (this.attr("class") || "").split(" ").indexOf(d)
            },
            removeClass: function(d) {
                return this.attr("class", (this.attr("class") || "").replace(c(d) ? new RegExp(" ?" + d + " ?") : d, ""))
            },
            symbolAttr: function(d) {
                var a = this;
                "x y r start end width height innerR anchorX anchorY clockwise".split(" ").forEach(function(c) {
                    a[c] = b(d[c], a[c])
                });
                a.attr({
                    d: a.renderer.symbols[a.symbolName](a.x, a.y, a.width, a.height, a)
                })
            },
            clip: function(d) {
                return this.attr("clip-path", d ? "url(" + this.renderer.url + "#" + d.id + ")": "none")
            },
            crisp: function(d, a) {
                a = a || d.strokeWidth || 0;
                var b = Math.round(a) % 2 / 2;
                d.x = Math.floor(d.x || this.x || 0) + b;
                d.y = Math.floor(d.y || this.y || 0) + b;
                d.width = Math.floor((d.width || this.width || 0) - 2 * b);
                d.height = Math.floor((d.height || this.height || 0) - 2 * b);
                z(d.strokeWidth) && (d.strokeWidth = a);
                return d
            },
            css: function(d) {
                var b = this.styles,
                c = {},
                h = this.element,
                e = "",
                l = !b,
                g = ["textOutline", "textOverflow", "width"];
                d && d.color && (d.fill = d.color);
                b && p(d,
                function(d, a) {
                    d !== b[a] && (c[a] = d, l = !0)
                });
                if (l) {
                    b && (d = t(b, c));
                    if (d) if (null === d.width || "auto" === d.width) delete this.textWidth;
                    else if ("text" === h.nodeName.toLowerCase() && d.width) var G = this.textWidth = a(d.width);
                    this.styles = d;
                    G && !O && this.renderer.forExport && delete d.width;
                    if (h.namespaceURI === this.SVG_NS) {
                        var K = function(d, a) {
                            return "-" + a.toLowerCase()
                        };
                        p(d,
                        function(d, a) { - 1 === g.indexOf(a) && (e += a.replace(/([A-Z])/g, K) + ":" + d + ";")
                        });
                        e && C(h, "style", e)
                    } else L(h, d);
                    this.added && ("text" === this.element.nodeName && this.renderer.buildText(this), d && d.textOutline && this.applyTextOutline(d.textOutline))
                }
                return this
            },
            getStyle: function(d) {
                return W.getComputedStyle(this.element || this, "").getPropertyValue(d)
            },
            strokeWidth: function() {
                if (!this.renderer.styledMode) return this["stroke-width"] || 0;
                var d = this.getStyle("stroke-width"),
                b = 0;
                if (d.indexOf("px") === d.length - 2) b = a(d);
                else if ("" !== d) {
                    var c = H.createElementNS(T, "rect");
                    C(c, {
                        width: d,
                        "stroke-width": 0
                    });
                    this.element.parentNode.appendChild(c);
                    b = c.getBBox().width;
                    c.parentNode.removeChild(c)
                }
                return b
            },
            on: function(d, a) {
                var b, c, h = this.element,
                e;
                M && "click" === d ? (h.ontouchstart = function(d) {
                    b = d.touches[0].clientX;
                    c = d.touches[0].clientY
                },
                h.ontouchend = function(d) {
                    b && 4 <= Math.sqrt(Math.pow(b - d.changedTouches[0].clientX, 2) + Math.pow(c - d.changedTouches[0].clientY, 2)) || a.call(h, d);
                    e = !0;
                    d.preventDefault()
                },
                h.onclick = function(d) {
                    e || a.call(h, d)
                }) : h["on" + d] = a;
                return this
            },
            setRadialReference: function(d) {
                var a = this.renderer.gradients[this.element.gradient];
                this.element.radialReference = d;
                a && a.radAttr && a.animate(this.renderer.getRadialAttr(d, a.radAttr));
                return this
            },
            translate: function(d, a) {
                return this.attr({
                    translateX: d,
                    translateY: a
                })
            },
            invert: function(d) {
                this.inverted = d;
                this.updateTransform();
                return this
            },
            updateTransform: function() {
                var d = this.translateX || 0,
                a = this.translateY || 0,
                c = this.scaleX,
                h = this.scaleY,
                e = this.inverted,
                l = this.rotation,
                g = this.matrix,
                G = this.element;
                e && (d += this.width, a += this.height);
                d = ["translate(" + d + "," + a + ")"];
                z(g) && d.push("matrix(" + g.join(",") + ")");
                e ? d.push("rotate(90) scale(-1,1)") : l && d.push("rotate(" + l + " " + b(this.rotationOriginX, G.getAttribute("x"), 0) + " " + b(this.rotationOriginY, G.getAttribute("y") || 0) + ")"); (z(c) || z(h)) && d.push("scale(" + b(c, 1) + " " + b(h, 1) + ")");
                d.length && G.setAttribute("transform", d.join(" "))
            },
            toFront: function() {
                var d = this.element;
                d.parentNode.appendChild(d);
                return this
            },
            align: function(d, a, h) {
                var e, l = {};
                var g = this.renderer;
                var G = g.alignedObjects;
                var I, K;
                if (d) {
                    if (this.alignOptions = d, this.alignByTranslate = a, !h || c(h)) this.alignTo = e = h || "renderer",
                    A(G, this),
                    G.push(this),
                    h = null
                } else d = this.alignOptions,
                a = this.alignByTranslate,
                e = this.alignTo;
                h = b(h, g[e], g);
                e = d.align;
                g = d.verticalAlign;
                G = (h.x || 0) + (d.x || 0);
                var p = (h.y || 0) + (d.y || 0);
                "right" === e ? I = 1 : "center" === e && (I = 2);
                I && (G += (h.width - (d.width || 0)) / I);
                l[a ? "translateX": "x"] = Math.round(G);
                "bottom" === g ? K = 1 : "middle" === g && (K = 2);
                K && (p += (h.height - (d.height || 0)) / K);
                l[a ? "translateY": "y"] = Math.round(p);
                this[this.placed ? "animate": "attr"](l);
                this.placed = !0;
                this.alignAttr = l;
                return this
            },
            getBBox: function(d, a) {
                var c, h = this.renderer,
                e = this.element,
                l = this.styles,
                g = this.textStr,
                G, I = h.cache,
                K = h.cacheKeys,
                p = e.namespaceURI === this.SVG_NS;
                a = b(a, this.rotation, 0);
                var O = h.styledMode ? e && R.prototype.getStyle.call(e, "font-size") : l && l.fontSize;
                if (z(g)) {
                    var v = g.toString(); - 1 === v.indexOf("<") && (v = v.replace(/[0-9]/g, "0"));
                    v += ["", a, O, this.textWidth, l && l.textOverflow].join()
                }
                v && !d && (c = I[v]);
                if (!c) {
                    if (p || h.forExport) {
                        try { (G = this.fakeTS &&
                            function(d) { [].forEach.call(e.querySelectorAll(".highcharts-text-outline"),
                                function(a) {
                                    a.style.display = d
                                })
                            }) && G("none"),
                            c = e.getBBox ? t({},
                            e.getBBox()) : {
                                width: e.offsetWidth,
                                height: e.offsetHeight
                            },
                            G && G("")
                        } catch(ea) {
                            ""
                        }
                        if (!c || 0 > c.width) c = {
                            width: 0,
                            height: 0
                        }
                    } else c = this.htmlGetBBox();
                    h.isSVG && (d = c.width, h = c.height, p && (c.height = h = {
                        "11px,17": 14,
                        "13px,20": 16
                    } [l && l.fontSize + "," + Math.round(h)] || h), a && (l = a * u, c.width = Math.abs(h * Math.sin(l)) + Math.abs(d * Math.cos(l)), c.height = Math.abs(h * Math.cos(l)) + Math.abs(d * Math.sin(l))));
                    if (v && 0 < c.height) {
                        for (; 250 < K.length;) delete I[K.shift()];
                        I[v] || K.push(v);
                        I[v] = c
                    }
                }
                return c
            },
            show: function(d) {
                return this.attr({
                    visibility: d ? "inherit": "visible"
                })
            },
            hide: function(d) {
                d ? this.attr({
                    y: -9999
                }) : this.attr({
                    visibility: "hidden"
                });
                return this
            },
            fadeOut: function(d) {
                var a = this;
                a.animate({
                    opacity: 0
                },
                {
                    duration: d || 150,
                    complete: function() {
                        a.attr({
                            y: -9999
                        })
                    }
                })
            },
            add: function(d) {
                var a = this.renderer,
                b = this.element;
                d && (this.parentGroup = d);
                this.parentInverted = d && d.inverted;
                "undefined" !== typeof this.textStr && a.buildText(this);
                this.added = !0;
                if (!d || d.handleZ || this.zIndex) var c = this.zIndexSetter();
                c || (d ? d.element: a.box).appendChild(b);
                if (this.onAdd) this.onAdd();
                return this
            },
            safeRemoveChild: function(d) {
                var a = d.parentNode;
                a && a.removeChild(d)
            },
            destroy: function() {
                var d = this,
                a = d.element || {},
                b = d.renderer,
                c = b.isSVG && "SPAN" === a.nodeName && d.parentGroup,
                h = a.ownerSVGElement,
                e = d.clipPath;
                a.onclick = a.onmouseout = a.onmouseover = a.onmousemove = a.point = null;
                w(d);
                e && h && ([].forEach.call(h.querySelectorAll("[clip-path],[CLIP-PATH]"),
                function(d) { - 1 < d.getAttribute("clip-path").indexOf(e.element.id) && d.removeAttribute("clip-path")
                }), d.clipPath = e.destroy());
                if (d.stops) {
                    for (h = 0; h < d.stops.length; h++) d.stops[h] = d.stops[h].destroy();
                    d.stops = null
                }
                d.safeRemoveChild(a);
                for (b.styledMode || d.destroyShadows(); c && c.div && 0 === c.div.childNodes.length;) a = c.parentGroup,
                d.safeRemoveChild(c.div),
                delete c.div,
                c = a;
                d.alignTo && A(b.alignedObjects, d);
                p(d,
                function(a, b) {
                    d[b] && d[b].parentGroup === d && d[b].destroy && d[b].destroy();
                    delete d[b]
                })
            },
            shadow: function(d, a, c) {
                var h = [],
                e,
                l = this.element;
                if (!d) this.destroyShadows();
                else if (!this.shadows) {
                    var g = b(d.width, 3);
                    var G = (d.opacity || .15) / g;
                    var I = this.parentInverted ? "(-1,-1)": "(" + b(d.offsetX, 1) + ", " + b(d.offsetY, 1) + ")";
                    for (e = 1; e <= g; e++) {
                        var p = l.cloneNode(0);
                        var O = 2 * g + 1 - 2 * e;
                        C(p, {
                            stroke: d.color || "#000000",
                            "stroke-opacity": G * e,
                            "stroke-width": O,
                            transform: "translate" + I,
                            fill: "none"
                        });
                        p.setAttribute("class", (p.getAttribute("class") || "") + " highcharts-shadow");
                        c && (C(p, "height", Math.max(C(p, "height") - O, 0)), p.cutHeight = O);
                        a ? a.element.appendChild(p) : l.parentNode && l.parentNode.insertBefore(p, l);
                        h.push(p)
                    }
                    this.shadows = h
                }
                return this
            },
            destroyShadows: function() { (this.shadows || []).forEach(function(d) {
                    this.safeRemoveChild(d)
                },
                this);
                this.shadows = void 0
            },
            xGetter: function(d) {
                "circle" === this.element.nodeName && ("x" === d ? d = "cx": "y" === d && (d = "cy"));
                return this._defaultGetter(d)
            },
            _defaultGetter: function(d) {
                d = b(this[d + "Value"], this[d], this.element ? this.element.getAttribute(d) : null, 0);
                /^[\-0-9\.]+$/.test(d) && (d = parseFloat(d));
                return d
            },
            dSetter: function(d, a, b) {
                d && d.join && (d = d.join(" "));
                /(NaN| {2}|^$)/.test(d) && (d = "M 0 0");
                this[a] !== d && (b.setAttribute(a, d), this[a] = d)
            },
            dashstyleSetter: function(d) {
                var b, c = this["stroke-width"];
                "inherit" === c && (c = 1);
                if (d = d && d.toLowerCase()) {
                    d = d.replace("shortdashdotdot", "3,1,1,1,1,1,").replace("shortdashdot", "3,1,1,1").replace("shortdot", "1,1,").replace("shortdash", "3,1,").replace("longdash", "8,3,").replace(/dot/g, "1,3,").replace("dash", "4,3,").replace(/,$/, "").split(",");
                    for (b = d.length; b--;) d[b] = a(d[b]) * c;
                    d = d.join(",").replace(/NaN/g, "none");
                    this.element.setAttribute("stroke-dasharray", d)
                }
            },
            alignSetter: function(d) {
                var a = {
                    left: "start",
                    center: "middle",
                    right: "end"
                };
                a[d] && (this.alignValue = d, this.element.setAttribute("text-anchor", a[d]))
            },
            opacitySetter: function(d, a, b) {
                this[a] = d;
                b.setAttribute(a, d)
            },
            titleSetter: function(d) {
                var a = this.element.getElementsByTagName("title")[0];
                a || (a = H.createElementNS(this.SVG_NS, "title"), this.element.appendChild(a));
                a.firstChild && a.removeChild(a.firstChild);
                a.appendChild(H.createTextNode(String(b(d, "")).replace(/<[^>]*>/g, "").replace(/&lt;/g, "<").replace(/&gt;/g, ">")))
            },
            textSetter: function(d) {
                d !== this.textStr && (delete this.bBox, delete this.textPxLength, this.textStr = d, this.added && this.renderer.buildText(this))
            },
            setTextPath: function(d, a) {
                var b = this.element,
                c = {
                    textAnchor: "text-anchor"
                },
                h = !1,
                e = this.textPathWrapper,
                G = !e;
                a = g(!0, {
                    enabled: !0,
                    attributes: {
                        dy: -5,
                        startOffset: "50%",
                        textAnchor: "middle"
                    }
                },
                a);
                var O = a.attributes;
                if (d && a && a.enabled) {
                    e && null === e.element.parentNode ? (G = !0, e = e.destroy()) : e && this.removeTextOutline.call(e.parentGroup, [].slice.call(b.getElementsByTagName("tspan")));
                    this.options && this.options.padding && (O.dx = -this.options.padding);
                    e || (this.textPathWrapper = e = this.renderer.createElement("textPath"), h = !0);
                    var v = e.element; (a = d.element.getAttribute("id")) || d.element.setAttribute("id", a = l());
                    if (G) for (d = b.getElementsByTagName("tspan"); d.length;) d[0].setAttribute("y", 0),
                    q(O.dx) && d[0].setAttribute("x", -O.dx),
                    v.appendChild(d[0]);
                    h && e.add({
                        element: this.text ? this.text.element: b
                    });
                    v.setAttributeNS("http://www.w3.org/1999/xlink", "href", this.renderer.url + "#" + a);
                    z(O.dy) && (v.parentNode.setAttribute("dy", O.dy), delete O.dy);
                    z(O.dx) && (v.parentNode.setAttribute("dx", O.dx), delete O.dx);
                    p(O,
                    function(d, a) {
                        v.setAttribute(c[a] || a, d)
                    });
                    b.removeAttribute("transform");
                    this.removeTextOutline.call(e, [].slice.call(b.getElementsByTagName("tspan")));
                    this.text && !this.renderer.styledMode && this.attr({
                        fill: "none",
                        "stroke-width": 0
                    });
                    this.applyTextOutline = this.updateTransform = I
                } else e && (delete this.updateTransform, delete this.applyTextOutline, this.destroyTextPath(b, d), this.updateTransform(), this.options.rotation && this.applyTextOutline(this.options.style.textOutline));
                return this
            },
            destroyTextPath: function(d, a) {
                var b = d.getElementsByTagName("text")[0];
                if (b) {
                    if (b.removeAttribute("dx"), b.removeAttribute("dy"), a.element.setAttribute("id", ""), b.getElementsByTagName("textPath").length) {
                        for (d = this.textPathWrapper.element.childNodes; d.length;) b.appendChild(d[0]);
                        b.removeChild(this.textPathWrapper.element)
                    }
                } else if (d.getAttribute("dx") || d.getAttribute("dy")) d.removeAttribute("dx"),
                d.removeAttribute("dy");
                this.textPathWrapper = this.textPathWrapper.destroy()
            },
            fillSetter: function(d, a, b) {
                "string" === typeof d ? b.setAttribute(a, d) : d && this.complexColor(d, a, b)
            },
            visibilitySetter: function(d, a, b) {
                "inherit" === d ? b.removeAttribute(a) : this[a] !== d && b.setAttribute(a, d);
                this[a] = d
            },
            zIndexSetter: function(d, b) {
                var c = this.renderer,
                h = this.parentGroup,
                e = (h || c).element || c.box,
                l = this.element,
                g = !1;
                c = e === c.box;
                var G = this.added;
                var I;
                z(d) ? (l.setAttribute("data-z-index", d), d = +d, this[b] === d && (G = !1)) : z(this[b]) && l.removeAttribute("data-z-index");
                this[b] = d;
                if (G) { (d = this.zIndex) && h && (h.handleZ = !0);
                    b = e.childNodes;
                    for (I = b.length - 1; 0 <= I && !g; I--) {
                        h = b[I];
                        G = h.getAttribute("data-z-index");
                        var p = !z(G);
                        if (h !== l) if (0 > d && p && !c && !I) e.insertBefore(l, b[I]),
                        g = !0;
                        else if (a(G) <= d || p && (!z(d) || 0 <= d)) e.insertBefore(l, b[I + 1] || null),
                        g = !0
                    }
                    g || (e.insertBefore(l, b[c ? 3 : 0] || null), g = !0)
                }
                return g
            },
            _defaultSetter: function(d, a, b) {
                b.setAttribute(a, d)
            }
        });
        R.prototype.yGetter = R.prototype.xGetter;
        R.prototype.translateXSetter = R.prototype.translateYSetter = R.prototype.rotationSetter = R.prototype.verticalAlignSetter = R.prototype.rotationOriginXSetter = R.prototype.rotationOriginYSetter = R.prototype.scaleXSetter = R.prototype.scaleYSetter = R.prototype.matrixSetter = function(d, a) {
            this[a] = d;
            this.doTransform = !0
        };
        R.prototype["stroke-widthSetter"] = R.prototype.strokeSetter = function(d, a, b) {
            this[a] = d;
            this.stroke && this["stroke-width"] ? (R.prototype.fillSetter.call(this, this.stroke, "stroke", b), b.setAttribute("stroke-width", this["stroke-width"]), this.hasStroke = !0) : "stroke-width" === a && 0 === d && this.hasStroke ? (b.removeAttribute("stroke"), this.hasStroke = !1) : this.renderer.styledMode && this["stroke-width"] && (b.setAttribute("stroke-width", this["stroke-width"]), this.hasStroke = !0)
        };
        m = f.SVGRenderer = function() {
            this.init.apply(this, arguments)
        };
        t(m.prototype, {
            Element: R,
            SVG_NS: T,
            init: function(a, b, c, h, e, l, g) {
                var G = this.createElement("svg").attr({
                    version: "1.1",
                    "class": "highcharts-root"
                });
                g || G.css(this.getStyle(h));
                h = G.element;
                a.appendChild(h);
                C(a, "dir", "ltr"); - 1 === a.innerHTML.indexOf("xmlns") && C(h, "xmlns", this.SVG_NS);
                this.isSVG = !0;
                this.box = h;
                this.boxWrapper = G;
                this.alignedObjects = [];
                this.url = (Q || d) && H.getElementsByTagName("base").length ? W.location.href.split("#")[0].replace(/<[^>]*>/g, "").replace(/([\('\)])/g, "\\$1").replace(/ /g, "%20") : "";
                this.createElement("desc").add().element.appendChild(H.createTextNode("Created with Highcharts 8.0.4"));
                this.defs = this.createElement("defs").add();
                this.allowHTML = l;
                this.forExport = e;
                this.styledMode = g;
                this.gradients = {};
                this.cache = {};
                this.cacheKeys = [];
                this.imgCount = 0;
                this.setSize(b, c, !1);
                var I;
                Q && a.getBoundingClientRect && (b = function() {
                    L(a, {
                        left: 0,
                        top: 0
                    });
                    I = a.getBoundingClientRect();
                    L(a, {
                        left: Math.ceil(I.left) - I.left + "px",
                        top: Math.ceil(I.top) - I.top + "px"
                    })
                },
                b(), this.unSubPixelFix = E(W, "resize", b))
            },
            definition: function(d) {
                function a(d, c) {
                    var h;
                    D(d).forEach(function(d) {
                        var e = b.createElement(d.tagName),
                        l = {};
                        p(d,
                        function(d, a) {
                            "tagName" !== a && "children" !== a && "textContent" !== a && (l[a] = d)
                        });
                        e.attr(l);
                        e.add(c || b.defs);
                        d.textContent && e.element.appendChild(H.createTextNode(d.textContent));
                        a(d.children || [], e);
                        h = e
                    });
                    return h
                }
                var b = this;
                return a(d)
            },
            getStyle: function(d) {
                return this.style = t({
                    fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                    fontSize: "12px"
                },
                d)
            },
            setStyle: function(d) {
                this.boxWrapper.css(this.getStyle(d))
            },
            isHidden: function() {
                return ! this.boxWrapper.getBBox().width
            },
            destroy: function() {
                var d = this.defs;
                this.box = null;
                this.boxWrapper = this.boxWrapper.destroy();
                x(this.gradients || {});
                this.gradients = null;
                d && (this.defs = d.destroy());
                this.unSubPixelFix && this.unSubPixelFix();
                return this.alignedObjects = null
            },
            createElement: function(d) {
                var a = new this.Element;
                a.init(this, d);
                return a
            },
            draw: I,
            getRadialAttr: function(d, a) {
                return {
                    cx: d[0] - d[2] / 2 + a.cx * d[2],
                    cy: d[1] - d[2] / 2 + a.cy * d[2],
                    r: a.r * d[2]
                }
            },
            truncate: function(d, a, b, c, h, e, l) {
                var g = this,
                G = d.rotation,
                I, p = c ? 1 : 0,
                O = (b || c).length,
                v = O,
                u = [],
                k = function(d) {
                    a.firstChild && a.removeChild(a.firstChild);
                    d && a.appendChild(H.createTextNode(d))
                },
                w = function(e, G) {
                    G = G || e;
                    if ("undefined" === typeof u[G]) if (a.getSubStringLength) try {
                        u[G] = h + a.getSubStringLength(0, c ? G + 1 : G)
                    } catch(ka) {
                        ""
                    } else g.getSpanWidth && (k(l(b || c, e)), u[G] = h + g.getSpanWidth(d, a));
                    return u[G]
                },
                D;
                d.rotation = 0;
                var K = w(a.textContent.length);
                if (D = h + K > e) {
                    for (; p <= O;) v = Math.ceil((p + O) / 2),
                    c && (I = l(c, v)),
                    K = w(v, I && I.length - 1),
                    p === O ? p = O + 1 : K > e ? O = v - 1 : p = v;
                    0 === O ? k("") : b && O === b.length - 1 || k(I || l(b || c, v))
                }
                c && c.splice(0, v);
                d.actualWidth = K;
                d.rotation = G;
                return D
            },
            escapes: {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                "'": "&#39;",
                '"': "&quot;"
            },
            buildText: function(d) {
                var c = d.element,
                h = this,
                e = h.forExport,
                l = b(d.textStr, "").toString(),
                g = -1 !== l.indexOf("<"),
                G = c.childNodes,
                I,
                v = C(c, "x"),
                u = d.styles,
                k = d.textWidth,
                w = u && u.lineHeight,
                D = u && u.textOutline,
                K = u && "ellipsis" === u.textOverflow,
                n = u && "nowrap" === u.whiteSpace,
                q = u && u.fontSize,
                V,
                f = G.length;
                u = k && !d.added && this.box;
                var M = function(d) {
                    var b;
                    h.styledMode || (b = /(px|em)$/.test(d && d.style.fontSize) ? d.style.fontSize: q || h.style.fontSize || 12);
                    return w ? a(w) : h.fontMetrics(b, d.getAttribute("style") ? d: c).h
                },
                t = function(d, a) {
                    p(h.escapes,
                    function(b, c) {
                        a && -1 !== a.indexOf(b) || (d = d.toString().replace(new RegExp(b, "g"), c))
                    });
                    return d
                },
                W = function(d, a) {
                    var b = d.indexOf("<");
                    d = d.substring(b, d.indexOf(">") - b);
                    b = d.indexOf(a + "=");
                    if ( - 1 !== b && (b = b + a.length + 1, a = d.charAt(b), '"' === a || "'" === a)) return d = d.substring(b + 1),
                    d.substring(0, d.indexOf(a))
                },
                x = /<br.*?>/g;
                var Q = [l, K, n, w, D, q, k].join();
                if (Q !== d.textCache) {
                    for (d.textCache = Q; f--;) c.removeChild(G[f]);
                    g || D || K || k || -1 !== l.indexOf(" ") && (!n || x.test(l)) ? (u && u.appendChild(c), g ? (l = h.styledMode ? l.replace(/<(b|strong)>/g, '<span class="highcharts-strong">').replace(/<(i|em)>/g, '<span class="highcharts-emphasized">') : l.replace(/<(b|strong)>/g, '<span style="font-weight:bold">').replace(/<(i|em)>/g, '<span style="font-style:italic">'), l = l.replace(/<a/g, "<span").replace(/<\/(b|strong|i|em|a)>/g, "</span>").split(x)) : l = [l], l = l.filter(function(d) {
                        return "" !== d
                    }), l.forEach(function(a, b) {
                        var l = 0,
                        g = 0;
                        a = a.replace(/^\s+|\s+$/g, "").replace(/<span/g, "|||<span").replace(/<\/span>/g, "</span>|||");
                        var G = a.split("|||");
                        G.forEach(function(a) {
                            if ("" !== a || 1 === G.length) {
                                var p = {},
                                u = H.createElementNS(h.SVG_NS, "tspan"),
                                w,
                                D; (w = W(a, "class")) && C(u, "class", w);
                                if (w = W(a, "style")) w = w.replace(/(;| |^)color([ :])/, "$1fill$2"),
                                C(u, "style", w); (D = W(a, "href")) && !e && (C(u, "onclick", 'location.href="' + D + '"'), C(u, "class", "highcharts-anchor"), h.styledMode || L(u, {
                                    cursor: "pointer"
                                }));
                                a = t(a.replace(/<[a-zA-Z\/](.|\n)*?>/g, "") || " ");
                                if (" " !== a) {
                                    u.appendChild(H.createTextNode(a));
                                    l ? p.dx = 0 : b && null !== v && (p.x = v);
                                    C(u, p);
                                    c.appendChild(u); ! l && V && (!O && e && L(u, {
                                        display: "block"
                                    }), C(u, "dy", M(u)));
                                    if (k) {
                                        var S = a.replace(/([^\^])-/g, "$1- ").split(" ");
                                        p = !n && (1 < G.length || b || 1 < S.length);
                                        D = 0;
                                        var f = M(u);
                                        if (K) I = h.truncate(d, u, a, void 0, 0, Math.max(0, k - parseInt(q || 12, 10)),
                                        function(d, a) {
                                            return d.substring(0, a) + "\u2026"
                                        });
                                        else if (p) for (; S.length;) S.length && !n && 0 < D && (u = H.createElementNS(T, "tspan"), C(u, {
                                            dy: f,
                                            x: v
                                        }), w && C(u, "style", w), u.appendChild(H.createTextNode(S.join(" ").replace(/- /g, "-"))), c.appendChild(u)),
                                        h.truncate(d, u, null, S, 0 === D ? g: 0, k,
                                        function(d, a) {
                                            return S.slice(0, a).join(" ").replace(/- /g, "-")
                                        }),
                                        g = d.actualWidth,
                                        D++
                                    }
                                    l++
                                }
                            }
                        });
                        V = V || c.childNodes.length
                    }), K && I && d.attr("title", t(d.textStr, ["&lt;", "&gt;"])), u && u.removeChild(c), D && d.applyTextOutline && d.applyTextOutline(D)) : c.appendChild(H.createTextNode(t(l)))
                }
            },
            getContrast: function(d) {
                d = r(d).rgba;
                d[0] *= 1;
                d[1] *= 1.2;
                d[2] *= .5;
                return 459 < d[0] + d[1] + d[2] ? "#000000": "#FFFFFF"
            },
            button: function(d, a, b, c, h, e, l, I, p, O) {
                var v = this.label(d, a, b, p, null, null, O, null, "button"),
                u = 0,
                k = this.styledMode;
                v.attr(g({
                    padding: 8,
                    r: 2
                },
                h));
                if (!k) {
                    h = g({
                        fill: "#f7f7f7",
                        stroke: "#cccccc",
                        "stroke-width": 1,
                        style: {
                            color: "#333333",
                            cursor: "pointer",
                            fontWeight: "normal"
                        }
                    },
                    h);
                    var w = h.style;
                    delete h.style;
                    e = g(h, {
                        fill: "#e6e6e6"
                    },
                    e);
                    var D = e.style;
                    delete e.style;
                    l = g(h, {
                        fill: "#e6ebf5",
                        style: {
                            color: "#000000",
                            fontWeight: "bold"
                        }
                    },
                    l);
                    var K = l.style;
                    delete l.style;
                    I = g(h, {
                        style: {
                            color: "#cccccc"
                        }
                    },
                    I);
                    var H = I.style;
                    delete I.style
                }
                E(v.element, G ? "mouseover": "mouseenter",
                function() {
                    3 !== u && v.setState(1)
                });
                E(v.element, G ? "mouseout": "mouseleave",
                function() {
                    3 !== u && v.setState(u)
                });
                v.setState = function(d) {
                    1 !== d && (v.state = u = d);
                    v.removeClass(/highcharts-button-(normal|hover|pressed|disabled)/).addClass("highcharts-button-" + ["normal", "hover", "pressed", "disabled"][d || 0]);
                    k || v.attr([h, e, l, I][d || 0]).css([w, D, K, H][d || 0])
                };
                k || v.attr(h).css(t({
                    cursor: "default"
                },
                w));
                return v.on("click",
                function(d) {
                    3 !== u && c.call(v, d)
                })
            },
            crispLine: function(d, a) {
                d[1] === d[4] && (d[1] = d[4] = Math.round(d[1]) - a % 2 / 2);
                d[2] === d[5] && (d[2] = d[5] = Math.round(d[2]) + a % 2 / 2);
                return d
            },
            path: function(d) {
                var a = this.styledMode ? {}: {
                    fill: "none"
                };
                k(d) ? a.d = d: e(d) && t(a, d);
                return this.createElement("path").attr(a)
            },
            circle: function(d, a, b) {
                d = e(d) ? d: "undefined" === typeof d ? {}: {
                    x: d,
                    y: a,
                    r: b
                };
                a = this.createElement("circle");
                a.xSetter = a.ySetter = function(d, a, b) {
                    b.setAttribute("c" + a, d)
                };
                return a.attr(d)
            },
            arc: function(d, a, b, c, h, l) {
                e(d) ? (c = d, a = c.y, b = c.r, d = c.x) : c = {
                    innerR: c,
                    start: h,
                    end: l
                };
                d = this.symbol("arc", d, a, b, b, c);
                d.r = b;
                return d
            },
            rect: function(d, a, b, c, h, l) {
                h = e(d) ? d.r: h;
                var g = this.createElement("rect");
                d = e(d) ? d: "undefined" === typeof d ? {}: {
                    x: d,
                    y: a,
                    width: Math.max(b, 0),
                    height: Math.max(c, 0)
                };
                this.styledMode || ("undefined" !== typeof l && (d.strokeWidth = l, d = g.crisp(d)), d.fill = "none");
                h && (d.r = h);
                g.rSetter = function(d, a, b) {
                    g.r = d;
                    C(b, {
                        rx: d,
                        ry: d
                    })
                };
                g.rGetter = function() {
                    return g.r
                };
                return g.attr(d)
            },
            setSize: function(d, a, c) {
                var h = this.alignedObjects,
                e = h.length;
                this.width = d;
                this.height = a;
                for (this.boxWrapper.animate({
                    width: d,
                    height: a
                },
                {
                    step: function() {
                        this.attr({
                            viewBox: "0 0 " + this.attr("width") + " " + this.attr("height")
                        })
                    },
                    duration: b(c, !0) ? void 0 : 0
                }); e--;) h[e].align()
            },
            g: function(d) {
                var a = this.createElement("g");
                return d ? a.attr({
                    "class": "highcharts-" + d
                }) : a
            },
            image: function(d, a, b, c, h, e) {
                var l = {
                    preserveAspectRatio: "none"
                },
                g = function(d, a) {
                    d.setAttributeNS ? d.setAttributeNS("http://www.w3.org/1999/xlink", "href", a) : d.setAttribute("hc-svg-href", a)
                },
                G = function(a) {
                    g(I.element, d);
                    e.call(I, a)
                };
                1 < arguments.length && t(l, {
                    x: a,
                    y: b,
                    width: c,
                    height: h
                });
                var I = this.createElement("image").attr(l);
                e ? (g(I.element, "data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="), l = new W.Image, E(l, "load", G), l.src = d, l.complete && G({})) : g(I.element, d);
                return I
            },
            symbol: function(d, a, c, e, l, g) {
                var G = this,
                I = /^url\((.*?)\)$/,
                p = I.test(d),
                O = !p && (this.symbols[d] ? d: "circle"),
                v = O && this.symbols[O],
                u = z(a) && v && v.call(this.symbols, Math.round(a), Math.round(c), e, l, g);
                if (v) {
                    var k = this.path(u);
                    G.styledMode || k.attr("fill", "none");
                    t(k, {
                        symbolName: O,
                        x: a,
                        y: c,
                        width: e,
                        height: l
                    });
                    g && t(k, g)
                } else if (p) {
                    var w = d.match(I)[1];
                    k = this.image(w);
                    k.imgwidth = b(V[w] && V[w].width, g && g.width);
                    k.imgheight = b(V[w] && V[w].height, g && g.height);
                    var D = function() {
                        k.attr({
                            width: k.width,
                            height: k.height
                        })
                    }; ["width", "height"].forEach(function(d) {
                        k[d + "Setter"] = function(d, a) {
                            var b = {},
                            c = this["img" + a],
                            h = "width" === a ? "translateX": "translateY";
                            this[a] = d;
                            z(c) && (g && "within" === g.backgroundSize && this.width && this.height && (c = Math.round(c * Math.min(this.width / this.imgwidth, this.height / this.imgheight))), this.element && this.element.setAttribute(a, c), this.alignByTranslate || (b[h] = ((this[a] || 0) - c) / 2, this.attr(b)))
                        }
                    });
                    z(a) && k.attr({
                        x: a,
                        y: c
                    });
                    k.isImg = !0;
                    z(k.imgwidth) && z(k.imgheight) ? D() : (k.attr({
                        width: 0,
                        height: 0
                    }), B("img", {
                        onload: function() {
                            var d = h[G.chartIndex];
                            0 === this.width && (L(this, {
                                position: "absolute",
                                top: "-999em"
                            }), H.body.appendChild(this));
                            V[w] = {
                                width: this.width,
                                height: this.height
                            };
                            k.imgwidth = this.width;
                            k.imgheight = this.height;
                            k.element && D();
                            this.parentNode && this.parentNode.removeChild(this);
                            G.imgCount--;
                            if (!G.imgCount && d && !d.hasLoaded) d.onload()
                        },
                        src: w
                    }), this.imgCount++)
                }
                return k
            },
            symbols: {
                circle: function(d, a, b, c) {
                    return this.arc(d + b / 2, a + c / 2, b / 2, c / 2, {
                        start: .5 * Math.PI,
                        end: 2.5 * Math.PI,
                        open: !1
                    })
                },
                square: function(d, a, b, c) {
                    return ["M", d, a, "L", d + b, a, d + b, a + c, d, a + c, "Z"]
                },
                triangle: function(d, a, b, c) {
                    return ["M", d + b / 2, a, "L", d + b, a + c, d, a + c, "Z"]
                },
                "triangle-down": function(d, a, b, c) {
                    return ["M", d, a, "L", d + b, a, d + b / 2, a + c, "Z"]
                },
                diamond: function(d, a, b, c) {
                    return ["M", d + b / 2, a, "L", d + b, a + c / 2, d + b / 2, a + c, d, a + c / 2, "Z"]
                },
                arc: function(d, a, c, h, e) {
                    var l = e.start,
                    g = e.r || c,
                    G = e.r || h || c,
                    I = e.end - .001;
                    c = e.innerR;
                    h = b(e.open, .001 > Math.abs(e.end - e.start - 2 * Math.PI));
                    var p = Math.cos(l),
                    O = Math.sin(l),
                    v = Math.cos(I);
                    I = Math.sin(I);
                    l = b(e.longArc, .001 > e.end - l - Math.PI ? 0 : 1);
                    g = ["M", d + g * p, a + G * O, "A", g, G, 0, l, b(e.clockwise, 1), d + g * v, a + G * I];
                    z(c) && g.push(h ? "M": "L", d + c * v, a + c * I, "A", c, c, 0, l, z(e.clockwise) ? 1 - e.clockwise: 0, d + c * p, a + c * O);
                    g.push(h ? "": "Z");
                    return g
                },
                callout: function(d, a, b, c, h) {
                    var e = Math.min(h && h.r || 0, b, c),
                    l = e + 6,
                    g = h && h.anchorX;
                    h = h && h.anchorY;
                    var G = ["M", d + e, a, "L", d + b - e, a, "C", d + b, a, d + b, a, d + b, a + e, "L", d + b, a + c - e, "C", d + b, a + c, d + b, a + c, d + b - e, a + c, "L", d + e, a + c, "C", d, a + c, d, a + c, d, a + c - e, "L", d, a + e, "C", d, a, d, a, d + e, a];
                    g && g > b ? h > a + l && h < a + c - l ? G.splice(13, 3, "L", d + b, h - 6, d + b + 6, h, d + b, h + 6, d + b, a + c - e) : G.splice(13, 3, "L", d + b, c / 2, g, h, d + b, c / 2, d + b, a + c - e) : g && 0 > g ? h > a + l && h < a + c - l ? G.splice(33, 3, "L", d, h + 6, d - 6, h, d, h - 6, d, a + e) : G.splice(33, 3, "L", d, c / 2, g, h, d, c / 2, d, a + e) : h && h > c && g > d + l && g < d + b - l ? G.splice(23, 3, "L", g + 6, a + c, g, a + c + 6, g - 6, a + c, d + e, a + c) : h && 0 > h && g > d + l && g < d + b - l && G.splice(3, 3, "L", g - 6, a, g, a - 6, g + 6, a, b - e, a);
                    return G
                }
            },
            clipRect: function(d, a, b, c) {
                var h = l() + "-",
                e = this.createElement("clipPath").attr({
                    id: h
                }).add(this.defs);
                d = this.rect(d, a, b, c, 0).add(e);
                d.id = h;
                d.clipPath = e;
                d.count = 0;
                return d
            },
            text: function(d, a, b, c) {
                var h = {};
                if (c && (this.allowHTML || !this.forExport)) return this.html(d, a, b);
                h.x = Math.round(a || 0);
                b && (h.y = Math.round(b));
                z(d) && (h.text = d);
                d = this.createElement("text").attr(h);
                c || (d.xSetter = function(d, a, b) {
                    var c = b.getElementsByTagName("tspan"),
                    h = b.getAttribute(a),
                    e;
                    for (e = 0; e < c.length; e++) {
                        var l = c[e];
                        l.getAttribute(a) === h && l.setAttribute(a, d)
                    }
                    b.setAttribute(a, d)
                });
                return d
            },
            fontMetrics: function(d, b) {
                d = !this.styledMode && /px/.test(d) || !W.getComputedStyle ? d || b && b.style && b.style.fontSize || this.style && this.style.fontSize: b && R.prototype.getStyle.call(b, "font-size");
                d = /px/.test(d) ? a(d) : 12;
                b = 24 > d ? d + 3 : Math.round(1.2 * d);
                return {
                    h: b,
                    b: Math.round(.8 * b),
                    f: d
                }
            },
            rotCorr: function(d, a, b) {
                var c = d;
                a && b && (c = Math.max(c * Math.cos(a * u), 4));
                return {
                    x: -d / 3 * Math.sin(a * u),
                    y: c
                }
            },
            label: function(d, a, b, c, h, e, l, G, I) {
                var p = this,
                O = p.styledMode,
                u = p.g("button" !== I && "label"),
                k = u.text = p.text("", 0, 0, l).attr({
                    zIndex: 1
                }),
                w,
                D,
                H = 0,
                n = 3,
                V = 0,
                T,
                f,
                M,
                S,
                W,
                x = {},
                Q,
                K,
                m = /^url\((.*?)\)$/.test(c),
                aa = O || m,
                A = function() {
                    return O ? w.strokeWidth() % 2 / 2 : (Q ? parseInt(Q, 10) : 0) % 2 / 2
                };
                I && u.addClass("highcharts-" + I);
                var r = function() {
                    var d = k.element.style,
                    a = {};
                    D = ("undefined" === typeof T || "undefined" === typeof f || W) && z(k.textStr) && k.getBBox();
                    u.width = (T || D.width || 0) + 2 * n + V;
                    u.height = (f || D.height || 0) + 2 * n;
                    K = n + Math.min(p.fontMetrics(d && d.fontSize, k).b, D ? D.height: Infinity);
                    aa && (w || (u.box = w = p.symbols[c] || m ? p.symbol(c) : p.rect(), w.addClass(("button" === I ? "": "highcharts-label-box") + (I ? " highcharts-" + I + "-box": "")), w.add(u), d = A(), a.x = d, a.y = (G ? -K: 0) + d), a.width = Math.round(u.width), a.height = Math.round(u.height), w.attr(t(a, x)), x = {})
                };
                var B = function() {
                    var d = V + n;
                    var a = G ? 0 : K;
                    z(T) && D && ("center" === W || "right" === W) && (d += {
                        center: .5,
                        right: 1
                    } [W] * (T - D.width));
                    if (d !== k.x || a !== k.y) k.attr("x", d),
                    k.hasBoxWidthChanged && (D = k.getBBox(!0), r()),
                    "undefined" !== typeof a && k.attr("y", a);
                    k.x = d;
                    k.y = a
                };
                var X = function(d, a) {
                    w ? w.attr(d, a) : x[d] = a
                };
                u.onAdd = function() {
                    k.add(u);
                    u.attr({
                        text: d || 0 === d ? d: "",
                        x: a,
                        y: b
                    });
                    w && z(h) && u.attr({
                        anchorX: h,
                        anchorY: e
                    })
                };
                u.widthSetter = function(d) {
                    T = q(d) ? d: null
                };
                u.heightSetter = function(d) {
                    f = d
                };
                u["text-alignSetter"] = function(d) {
                    W = d
                };
                u.paddingSetter = function(d) {
                    z(d) && d !== n && (n = u.padding = d, B())
                };
                u.paddingLeftSetter = function(d) {
                    z(d) && d !== V && (V = d, B())
                };
                u.alignSetter = function(d) {
                    d = {
                        left: 0,
                        center: .5,
                        right: 1
                    } [d];
                    d !== H && (H = d, D && u.attr({
                        x: M
                    }))
                };
                u.textSetter = function(d) {
                    "undefined" !== typeof d && k.attr({
                        text: d
                    });
                    r();
                    B()
                };
                u["stroke-widthSetter"] = function(d, a) {
                    d && (aa = !0);
                    Q = this["stroke-width"] = d;
                    X(a, d)
                };
                O ? u.rSetter = function(d, a) {
                    X(a, d)
                }: u.strokeSetter = u.fillSetter = u.rSetter = function(d, a) {
                    "r" !== a && ("fill" === a && d && (aa = !0), u[a] = d);
                    X(a, d)
                };
                u.anchorXSetter = function(d, a) {
                    h = u.anchorX = d;
                    X(a, Math.round(d) - A() - M)
                };
                u.anchorYSetter = function(d, a) {
                    e = u.anchorY = d;
                    X(a, d - S)
                };
                u.xSetter = function(d) {
                    u.x = d;
                    H && (d -= H * ((T || D.width) + 2 * n), u["forceAnimate:x"] = !0);
                    M = Math.round(d);
                    u.attr("translateX", M)
                };
                u.ySetter = function(d) {
                    S = u.y = Math.round(d);
                    u.attr("translateY", S)
                };
                var C = u.css;
                l = {
                    css: function(d) {
                        if (d) {
                            var a = {};
                            d = g(d);
                            u.textProps.forEach(function(b) {
                                "undefined" !== typeof d[b] && (a[b] = d[b], delete d[b])
                            });
                            k.css(a);
                            "width" in a && r();
                            "fontSize" in a && (r(), B())
                        }
                        return C.call(u, d)
                    },
                    getBBox: function() {
                        return {
                            width: D.width + 2 * n,
                            height: D.height + 2 * n,
                            x: D.x - n,
                            y: D.y - n
                        }
                    },
                    destroy: function() {
                        v(u.element, "mouseenter");
                        v(u.element, "mouseleave");
                        k && (k = k.destroy());
                        w && (w = w.destroy());
                        R.prototype.destroy.call(u);
                        u = p = r = B = X = null
                    }
                };
                O || (l.shadow = function(d) {
                    d && (r(), w && w.shadow(d));
                    return u
                });
                return t(u, l)
            }
        });
        f.Renderer = m
    });
    P(y, "parts/Html.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.attr,
        r = m.createElement,
        E = m.css,
        N = m.defined,
        F = m.extend,
        C = m.pick,
        B = m.pInt,
        L = f.isFirefox,
        z = f.isMS,
        x = f.isWebKit,
        A = f.SVGElement;
        m = f.SVGRenderer;
        var t = f.win;
        F(A.prototype, {
            htmlCss: function(n) {
                var k = "SPAN" === this.element.tagName && n && "width" in n,
                q = C(k && n.width, void 0);
                if (k) {
                    delete n.width;
                    this.textWidth = q;
                    var e = !0
                }
                n && "ellipsis" === n.textOverflow && (n.whiteSpace = "nowrap", n.overflow = "hidden");
                this.styles = F(this.styles, n);
                E(this.element, n);
                e && this.htmlUpdateTransform();
                return this
            },
            htmlGetBBox: function() {
                var n = this.element;
                return {
                    x: n.offsetLeft,
                    y: n.offsetTop,
                    width: n.offsetWidth,
                    height: n.offsetHeight
                }
            },
            htmlUpdateTransform: function() {
                if (this.added) {
                    var n = this.renderer,
                    k = this.element,
                    q = this.translateX || 0,
                    e = this.translateY || 0,
                    c = this.x || 0,
                    g = this.y || 0,
                    p = this.textAlign || "left",
                    b = {
                        left: 0,
                        center: .5,
                        right: 1
                    } [p],
                    a = this.styles,
                    v = a && a.whiteSpace;
                    E(k, {
                        marginLeft: q,
                        marginTop: e
                    }); ! n.styledMode && this.shadows && this.shadows.forEach(function(a) {
                        E(a, {
                            marginLeft: q + 1,
                            marginTop: e + 1
                        })
                    });
                    this.inverted && [].forEach.call(k.childNodes,
                    function(a) {
                        n.invertChild(a, k)
                    });
                    if ("SPAN" === k.tagName) {
                        a = this.rotation;
                        var D = this.textWidth && B(this.textWidth),
                        w = [a, p, k.innerHTML, this.textWidth, this.textAlign].join(),
                        l; (l = D !== this.oldTextWidth) && !(l = D > this.oldTextWidth) && ((l = this.textPxLength) || (E(k, {
                            width: "",
                            whiteSpace: v || "nowrap"
                        }), l = k.offsetWidth), l = l > D);
                        l && (/[ \-]/.test(k.textContent || k.innerText) || "ellipsis" === k.style.textOverflow) ? (E(k, {
                            width: D + "px",
                            display: "block",
                            whiteSpace: v || "normal"
                        }), this.oldTextWidth = D, this.hasBoxWidthChanged = !0) : this.hasBoxWidthChanged = !1;
                        w !== this.cTT && (v = n.fontMetrics(k.style.fontSize, k).b, !N(a) || a === (this.oldRotation || 0) && p === this.oldAlign || this.setSpanRotation(a, b, v), this.getSpanCorrection(!N(a) && this.textPxLength || k.offsetWidth, v, b, a, p));
                        E(k, {
                            left: c + (this.xCorr || 0) + "px",
                            top: g + (this.yCorr || 0) + "px"
                        });
                        this.cTT = w;
                        this.oldRotation = a;
                        this.oldAlign = p
                    }
                } else this.alignOnAdd = !0
            },
            setSpanRotation: function(n, k, q) {
                var e = {},
                c = this.renderer.getTransformKey();
                e[c] = e.transform = "rotate(" + n + "deg)";
                e[c + (L ? "Origin": "-origin")] = e.transformOrigin = 100 * k + "% " + q + "px";
                E(this.element, e)
            },
            getSpanCorrection: function(n, k, q) {
                this.xCorr = -n * q;
                this.yCorr = -k
            }
        });
        F(m.prototype, {
            getTransformKey: function() {
                return z && !/Edge/.test(t.navigator.userAgent) ? "-ms-transform": x ? "-webkit-transform": L ? "MozTransform": t.opera ? "-o-transform": ""
            },
            html: function(n, k, q) {
                var e = this.createElement("span"),
                c = e.element,
                g = e.renderer,
                p = g.isSVG,
                b = function(a, b) { ["opacity", "visibility"].forEach(function(c) {
                        a[c + "Setter"] = function(e, l, h) {
                            var g = a.div ? a.div.style: b;
                            A.prototype[c + "Setter"].call(this, e, l, h);
                            g && (g[l] = e)
                        }
                    });
                    a.addedSetters = !0
                };
                e.textSetter = function(a) {
                    a !== c.innerHTML && (delete this.bBox, delete this.oldTextWidth);
                    this.textStr = a;
                    c.innerHTML = C(a, "");
                    e.doTransform = !0
                };
                p && b(e, e.element.style);
                e.xSetter = e.ySetter = e.alignSetter = e.rotationSetter = function(a, b) {
                    "align" === b && (b = "textAlign");
                    e[b] = a;
                    e.doTransform = !0
                };
                e.afterSetters = function() {
                    this.doTransform && (this.htmlUpdateTransform(), this.doTransform = !1)
                };
                e.attr({
                    text: n,
                    x: Math.round(k),
                    y: Math.round(q)
                }).css({
                    position: "absolute"
                });
                g.styledMode || e.css({
                    fontFamily: this.style.fontFamily,
                    fontSize: this.style.fontSize
                });
                c.style.whiteSpace = "nowrap";
                e.css = e.htmlCss;
                p && (e.add = function(a) {
                    var p = g.box.parentNode,
                    k = [];
                    if (this.parentGroup = a) {
                        var w = a.div;
                        if (!w) {
                            for (; a;) k.push(a),
                            a = a.parentGroup;
                            k.reverse().forEach(function(a) {
                                function c(b, c) {
                                    a[c] = b;
                                    "translateX" === c ? g.left = b + "px": g.top = b + "px";
                                    a.doTransform = !0
                                }
                                var l = J(a.element, "class");
                                w = a.div = a.div || r("div", l ? {
                                    className: l
                                }: void 0, {
                                    position: "absolute",
                                    left: (a.translateX || 0) + "px",
                                    top: (a.translateY || 0) + "px",
                                    display: a.display,
                                    opacity: a.opacity,
                                    pointerEvents: a.styles && a.styles.pointerEvents
                                },
                                w || p);
                                var g = w.style;
                                F(a, {
                                    classSetter: function(a) {
                                        return function(b) {
                                            this.element.setAttribute("class", b);
                                            a.className = b
                                        }
                                    } (w),
                                    on: function() {
                                        k[0].div && e.on.apply({
                                            element: k[0].div
                                        },
                                        arguments);
                                        return a
                                    },
                                    translateXSetter: c,
                                    translateYSetter: c
                                });
                                a.addedSetters || b(a)
                            })
                        }
                    } else w = p;
                    w.appendChild(c);
                    e.added = !0;
                    e.alignOnAdd && e.htmlUpdateTransform();
                    return e
                });
                return e
            }
        })
    });
    P(y, "parts/Tick.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.clamp,
        r = m.correctFloat,
        E = m.defined,
        N = m.destroyObjectProperties,
        F = m.extend,
        C = m.isNumber,
        B = m.merge,
        L = m.objectEach,
        z = m.pick,
        x = f.fireEvent,
        A = f.deg2rad;
        m = function() {
            function t(n, k, q, e, c) {
                this.isNewLabel = this.isNew = !0;
                this.axis = n;
                this.pos = k;
                this.type = q || "";
                this.parameters = c || {};
                this.tickmarkOffset = this.parameters.tickmarkOffset;
                this.options = this.parameters.options;
                q || e || this.addLabel()
            }
            t.prototype.addLabel = function() {
                var n = this,
                k = n.axis,
                q = k.options,
                e = k.chart,
                c = k.categories,
                g = k.names,
                p = n.pos,
                b = z(n.options && n.options.labels, q.labels),
                a = k.tickPositions,
                v = p === a[0],
                D = p === a[a.length - 1];
                g = this.parameters.category || (c ? z(c[p], g[p], p) : p);
                var w = n.label;
                c = (!b.step || 1 === b.step) && 1 === k.tickInterval;
                a = a.info;
                var l, h;
                if (k.isDatetimeAxis && a) {
                    var u = e.time.resolveDTLFormat(q.dateTimeLabelFormats[!q.grid && a.higherRanks[p] || a.unitName]);
                    var H = u.main
                }
                n.isFirst = v;
                n.isLast = D;
                n.formatCtx = {
                    axis: k,
                    chart: e,
                    isFirst: v,
                    isLast: D,
                    dateTimeLabelFormat: H,
                    tickPositionInfo: a,
                    value: k.isLog ? r(k.lin2log(g)) : g,
                    pos: p
                };
                q = k.labelFormatter.call(n.formatCtx, this.formatCtx);
                if (h = u && u.list) n.shortenLabel = function() {
                    for (l = 0; l < h.length; l++) if (w.attr({
                        text: k.labelFormatter.call(F(n.formatCtx, {
                            dateTimeLabelFormat: h[l]
                        }))
                    }), w.getBBox().width < k.getSlotWidth(n) - 2 * z(b.padding, 5)) return;
                    w.attr({
                        text: ""
                    })
                };
                c && k._addedPlotLB && k.isXAxis && n.moveLabel(q, b);
                E(w) || n.movedLabel ? w && w.textStr !== q && !c && (!w.textWidth || b.style && b.style.width || w.styles.width || w.css({
                    width: null
                }), w.attr({
                    text: q
                }), w.textPxLength = w.getBBox().width) : (n.label = w = n.createLabel({
                    x: 0,
                    y: 0
                },
                q, b), n.rotation = 0)
            };
            t.prototype.createLabel = function(n, k, q) {
                var e = this.axis,
                c = e.chart;
                if (n = E(k) && q.enabled ? c.renderer.text(k, n.x, n.y, q.useHTML).add(e.labelGroup) : null) c.styledMode || n.css(B(q.style)),
                n.textPxLength = n.getBBox().width;
                return n
            };
            t.prototype.destroy = function() {
                N(this, this.axis)
            };
            t.prototype.getPosition = function(n, k, q, e) {
                var c = this.axis,
                g = c.chart,
                p = e && g.oldChartHeight || g.chartHeight;
                n = {
                    x: n ? r(c.translate(k + q, null, null, e) + c.transB) : c.left + c.offset + (c.opposite ? (e && g.oldChartWidth || g.chartWidth) - c.right - c.left: 0),
                    y: n ? p - c.bottom + c.offset - (c.opposite ? c.height: 0) : r(p - c.translate(k + q, null, null, e) - c.transB)
                };
                n.y = J(n.y, -1E5, 1E5);
                x(this, "afterGetPosition", {
                    pos: n
                });
                return n
            };
            t.prototype.getLabelPosition = function(n, k, q, e, c, g, p, b) {
                var a = this.axis,
                v = a.transA,
                D = a.isLinked && a.linkedParent ? a.linkedParent.reversed: a.reversed,
                w = a.staggerLines,
                l = a.tickRotCorr || {
                    x: 0,
                    y: 0
                },
                h = c.y,
                u = e || a.reserveSpaceDefault ? 0 : -a.labelOffset * ("center" === a.labelAlign ? .5 : 1),
                H = {};
                E(h) || (h = 0 === a.side ? q.rotation ? -8 : -q.getBBox().height: 2 === a.side ? l.y + 8 : Math.cos(q.rotation * A) * (l.y - q.getBBox(!1, 0).height / 2));
                n = n + c.x + u + l.x - (g && e ? g * v * (D ? -1 : 1) : 0);
                k = k + h - (g && !e ? g * v * (D ? 1 : -1) : 0);
                w && (q = p / (b || 1) % w, a.opposite && (q = w - q - 1), k += a.labelOffset / w * q);
                H.x = n;
                H.y = Math.round(k);
                x(this, "afterGetLabelPosition", {
                    pos: H,
                    tickmarkOffset: g,
                    index: p
                });
                return H
            };
            t.prototype.getLabelSize = function() {
                return this.label ? this.label.getBBox()[this.axis.horiz ? "height": "width"] : 0
            };
            t.prototype.getMarkPath = function(n, k, q, e, c, g) {
                return g.crispLine(["M", n, k, "L", n + (c ? 0 : -q), k + (c ? q: 0)], e)
            };
            t.prototype.handleOverflow = function(n) {
                var k = this.axis,
                q = k.options.labels,
                e = n.x,
                c = k.chart.chartWidth,
                g = k.chart.spacing,
                p = z(k.labelLeft, Math.min(k.pos, g[3]));
                g = z(k.labelRight, Math.max(k.isRadial ? 0 : k.pos + k.len, c - g[1]));
                var b = this.label,
                a = this.rotation,
                v = {
                    left: 0,
                    center: .5,
                    right: 1
                } [k.labelAlign || b.attr("align")],
                D = b.getBBox().width,
                w = k.getSlotWidth(this),
                l = w,
                h = 1,
                u,
                H = {};
                if (a || "justify" !== z(q.overflow, "justify")) 0 > a && e - v * D < p ? u = Math.round(e / Math.cos(a * A) - p) : 0 < a && e + v * D > g && (u = Math.round((c - e) / Math.cos(a * A)));
                else if (c = e + (1 - v) * D, e - v * D < p ? l = n.x + l * (1 - v) - p: c > g && (l = g - n.x + l * v, h = -1), l = Math.min(w, l), l < w && "center" === k.labelAlign && (n.x += h * (w - l - v * (w - Math.min(D, l)))), D > l || k.autoRotation && (b.styles || {}).width) u = l;
                u && (this.shortenLabel ? this.shortenLabel() : (H.width = Math.floor(u), (q.style || {}).textOverflow || (H.textOverflow = "ellipsis"), b.css(H)))
            };
            t.prototype.moveLabel = function(n, k) {
                var q = this,
                e = q.label,
                c = !1,
                g = q.axis,
                p = g.reversed,
                b = g.chart.inverted;
                e && e.textStr === n ? (q.movedLabel = e, c = !0, delete q.label) : L(g.ticks,
                function(a) {
                    c || a.isNew || a === q || !a.label || a.label.textStr !== n || (q.movedLabel = a.label, c = !0, a.labelPos = q.movedLabel.xy, delete a.label)
                });
                if (!c && (q.labelPos || e)) {
                    var a = q.labelPos || e.xy;
                    e = b ? a.x: p ? 0 : g.width + g.left;
                    g = b ? p ? g.width + g.left: 0 : a.y;
                    q.movedLabel = q.createLabel({
                        x: e,
                        y: g
                    },
                    n, k);
                    q.movedLabel && q.movedLabel.attr({
                        opacity: 0
                    })
                }
            };
            t.prototype.render = function(n, k, q) {
                var e = this.axis,
                c = e.horiz,
                g = this.pos,
                p = z(this.tickmarkOffset, e.tickmarkOffset);
                g = this.getPosition(c, g, p, k);
                p = g.x;
                var b = g.y;
                e = c && p === e.pos + e.len || !c && b === e.pos ? -1 : 1;
                q = z(q, 1);
                this.isActive = !0;
                this.renderGridLine(k, q, e);
                this.renderMark(g, q, e);
                this.renderLabel(g, k, q, n);
                this.isNew = !1;
                f.fireEvent(this, "afterRender")
            };
            t.prototype.renderGridLine = function(n, k, q) {
                var e = this.axis,
                c = e.options,
                g = this.gridLine,
                p = {},
                b = this.pos,
                a = this.type,
                v = z(this.tickmarkOffset, e.tickmarkOffset),
                D = e.chart.renderer,
                w = a ? a + "Grid": "grid",
                l = c[w + "LineWidth"],
                h = c[w + "LineColor"];
                c = c[w + "LineDashStyle"];
                g || (e.chart.styledMode || (p.stroke = h, p["stroke-width"] = l, c && (p.dashstyle = c)), a || (p.zIndex = 1), n && (k = 0), this.gridLine = g = D.path().attr(p).addClass("highcharts-" + (a ? a + "-": "") + "grid-line").add(e.gridGroup));
                if (g && (q = e.getPlotLinePath({
                    value: b + v,
                    lineWidth: g.strokeWidth() * q,
                    force: "pass",
                    old: n
                }))) g[n || this.isNew ? "attr": "animate"]({
                    d: q,
                    opacity: k
                })
            };
            t.prototype.renderMark = function(n, k, q) {
                var e = this.axis,
                c = e.options,
                g = e.chart.renderer,
                p = this.type,
                b = p ? p + "Tick": "tick",
                a = e.tickSize(b),
                v = this.mark,
                D = !v,
                w = n.x;
                n = n.y;
                var l = z(c[b + "Width"], !p && e.isXAxis ? 1 : 0);
                c = c[b + "Color"];
                a && (e.opposite && (a[0] = -a[0]), D && (this.mark = v = g.path().addClass("highcharts-" + (p ? p + "-": "") + "tick").add(e.axisGroup), e.chart.styledMode || v.attr({
                    stroke: c,
                    "stroke-width": l
                })), v[D ? "attr": "animate"]({
                    d: this.getMarkPath(w, n, a[0], v.strokeWidth() * q, e.horiz, g),
                    opacity: k
                }))
            };
            t.prototype.renderLabel = function(n, k, q, e) {
                var c = this.axis,
                g = c.horiz,
                p = c.options,
                b = this.label,
                a = p.labels,
                v = a.step;
                c = z(this.tickmarkOffset, c.tickmarkOffset);
                var D = !0,
                w = n.x;
                n = n.y;
                b && C(w) && (b.xy = n = this.getLabelPosition(w, n, b, g, a, c, e, v), this.isFirst && !this.isLast && !z(p.showFirstLabel, 1) || this.isLast && !this.isFirst && !z(p.showLastLabel, 1) ? D = !1 : !g || a.step || a.rotation || k || 0 === q || this.handleOverflow(n), v && e % v && (D = !1), D && C(n.y) ? (n.opacity = q, b[this.isNewLabel ? "attr": "animate"](n), this.isNewLabel = !1) : (b.attr("y", -9999), this.isNewLabel = !0))
            };
            t.prototype.replaceMovedLabel = function() {
                var n = this.label,
                k = this.axis,
                q = k.reversed,
                e = this.axis.chart.inverted;
                if (n && !this.isNew) {
                    var c = e ? n.xy.x: q ? k.left: k.width + k.left;
                    q = e ? q ? k.width + k.top: k.top: n.xy.y;
                    n.animate({
                        x: c,
                        y: q,
                        opacity: 0
                    },
                    void 0, n.destroy);
                    delete this.label
                }
                k.isDirty = !0;
                this.label = this.movedLabel;
                delete this.movedLabel
            };
            return t
        } ();
        f.Tick = m;
        return f.Tick
    });
    P(y, "parts/Time.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.defined,
        r = m.error,
        E = m.extend,
        N = m.isObject,
        F = m.merge,
        C = m.objectEach,
        B = m.pad,
        L = m.pick,
        z = m.splat,
        x = m.timeUnits,
        A = f.win;
        m = function() {
            function t(n) {
                this.options = {};
                this.variableTimezone = this.useUTC = !1;
                this.Date = A.Date;
                this.getTimezoneOffset = this.timezoneOffsetFunction();
                this.update(n)
            }
            t.prototype.get = function(n, k) {
                if (this.variableTimezone || this.timezoneOffset) {
                    var q = k.getTime(),
                    e = q - this.getTimezoneOffset(k);
                    k.setTime(e);
                    n = k["getUTC" + n]();
                    k.setTime(q);
                    return n
                }
                return this.useUTC ? k["getUTC" + n]() : k["get" + n]()
            };
            t.prototype.set = function(n, k, q) {
                if (this.variableTimezone || this.timezoneOffset) {
                    if ("Milliseconds" === n || "Seconds" === n || "Minutes" === n) return k["setUTC" + n](q);
                    var e = this.getTimezoneOffset(k);
                    e = k.getTime() - e;
                    k.setTime(e);
                    k["setUTC" + n](q);
                    n = this.getTimezoneOffset(k);
                    e = k.getTime() + n;
                    return k.setTime(e)
                }
                return this.useUTC ? k["setUTC" + n](q) : k["set" + n](q)
            };
            t.prototype.update = function(n) {
                var k = L(n && n.useUTC, !0);
                this.options = n = F(!0, this.options || {},
                n);
                this.Date = n.Date || A.Date || Date;
                this.timezoneOffset = (this.useUTC = k) && n.timezoneOffset;
                this.getTimezoneOffset = this.timezoneOffsetFunction();
                this.variableTimezone = !(k && !n.getTimezoneOffset && !n.timezone)
            };
            t.prototype.makeTime = function(n, k, q, e, c, g) {
                if (this.useUTC) {
                    var p = this.Date.UTC.apply(0, arguments);
                    var b = this.getTimezoneOffset(p);
                    p += b;
                    var a = this.getTimezoneOffset(p);
                    b !== a ? p += a - b: b - 36E5 !== this.getTimezoneOffset(p - 36E5) || f.isSafari || (p -= 36E5)
                } else p = (new this.Date(n, k, L(q, 1), L(e, 0), L(c, 0), L(g, 0))).getTime();
                return p
            };
            t.prototype.timezoneOffsetFunction = function() {
                var n = this,
                k = this.options,
                q = A.moment;
                if (!this.useUTC) return function(e) {
                    return 6E4 * (new Date(e.toString())).getTimezoneOffset()
                };
                if (k.timezone) {
                    if (q) return function(e) {
                        return 6E4 * -q.tz(e, k.timezone).utcOffset()
                    };
                    r(25)
                }
                return this.useUTC && k.getTimezoneOffset ?
                function(e) {
                    return 6E4 * k.getTimezoneOffset(e.valueOf())
                }: function() {
                    return 6E4 * (n.timezoneOffset || 0)
                }
            };
            t.prototype.dateFormat = function(n, k, q) {
                var e;
                if (!J(k) || isNaN(k)) return (null === (e = f.defaultOptions.lang) || void 0 === e ? void 0 : e.invalidDate) || "";
                n = L(n, "%Y-%m-%d %H:%M:%S");
                var c = this;
                e = new this.Date(k);
                var g = this.get("Hours", e),
                p = this.get("Day", e),
                b = this.get("Date", e),
                a = this.get("Month", e),
                v = this.get("FullYear", e),
                D = f.defaultOptions.lang,
                w = null === D || void 0 === D ? void 0 : D.weekdays,
                l = null === D || void 0 === D ? void 0 : D.shortWeekdays;
                e = E({
                    a: l ? l[p] : w[p].substr(0, 3),
                    A: w[p],
                    d: B(b),
                    e: B(b, 2, " "),
                    w: p,
                    b: D.shortMonths[a],
                    B: D.months[a],
                    m: B(a + 1),
                    o: a + 1,
                    y: v.toString().substr(2, 2),
                    Y: v,
                    H: B(g),
                    k: g,
                    I: B(g % 12 || 12),
                    l: g % 12 || 12,
                    M: B(this.get("Minutes", e)),
                    p: 12 > g ? "AM": "PM",
                    P: 12 > g ? "am": "pm",
                    S: B(e.getSeconds()),
                    L: B(Math.floor(k % 1E3), 3)
                },
                f.dateFormats);
                C(e,
                function(a, b) {
                    for (; - 1 !== n.indexOf("%" + b);) n = n.replace("%" + b, "function" === typeof a ? a.call(c, k) : a)
                });
                return q ? n.substr(0, 1).toUpperCase() + n.substr(1) : n
            };
            t.prototype.resolveDTLFormat = function(n) {
                return N(n, !0) ? n: (n = z(n), {
                    main: n[0],
                    from: n[1],
                    to: n[2]
                })
            };
            t.prototype.getTimeTicks = function(n, k, q, e) {
                var c = this,
                g = [],
                p = {};
                var b = new c.Date(k);
                var a = n.unitRange,
                v = n.count || 1,
                D;
                e = L(e, 1);
                if (J(k)) {
                    c.set("Milliseconds", b, a >= x.second ? 0 : v * Math.floor(c.get("Milliseconds", b) / v));
                    a >= x.second && c.set("Seconds", b, a >= x.minute ? 0 : v * Math.floor(c.get("Seconds", b) / v));
                    a >= x.minute && c.set("Minutes", b, a >= x.hour ? 0 : v * Math.floor(c.get("Minutes", b) / v));
                    a >= x.hour && c.set("Hours", b, a >= x.day ? 0 : v * Math.floor(c.get("Hours", b) / v));
                    a >= x.day && c.set("Date", b, a >= x.month ? 1 : Math.max(1, v * Math.floor(c.get("Date", b) / v)));
                    if (a >= x.month) {
                        c.set("Month", b, a >= x.year ? 0 : v * Math.floor(c.get("Month", b) / v));
                        var w = c.get("FullYear", b)
                    }
                    a >= x.year && c.set("FullYear", b, w - w % v);
                    a === x.week && (w = c.get("Day", b), c.set("Date", b, c.get("Date", b) - w + e + (w < e ? -7 : 0)));
                    w = c.get("FullYear", b);
                    e = c.get("Month", b);
                    var l = c.get("Date", b),
                    h = c.get("Hours", b);
                    k = b.getTime();
                    c.variableTimezone && (D = q - k > 4 * x.month || c.getTimezoneOffset(k) !== c.getTimezoneOffset(q));
                    k = b.getTime();
                    for (b = 1; k < q;) g.push(k),
                    k = a === x.year ? c.makeTime(w + b * v, 0) : a === x.month ? c.makeTime(w, e + b * v) : !D || a !== x.day && a !== x.week ? D && a === x.hour && 1 < v ? c.makeTime(w, e, l, h + b * v) : k + a * v: c.makeTime(w, e, l + b * v * (a === x.day ? 1 : 7)),
                    b++;
                    g.push(k);
                    a <= x.hour && 1E4 > g.length && g.forEach(function(a) {
                        0 === a % 18E5 && "000000000" === c.dateFormat("%H%M%S%L", a) && (p[a] = "day")
                    })
                }
                g.info = E(n, {
                    higherRanks: p,
                    totalRange: a * v
                });
                return g
            };
            t.defaultOptions = {
                Date: void 0,
                getTimezoneOffset: void 0,
                timezone: void 0,
                timezoneOffset: 0,
                useUTC: !0
            };
            return t
        } ();
        f.Time = m;
        return f.Time
    });
    P(y, "parts/Options.js", [y["parts/Globals.js"], y["parts/Time.js"], y["parts/Color.js"], y["parts/Utilities.js"]],
    function(f, m, J, r) {
        J = J.parse;
        var E = r.merge;
        f.defaultOptions = {
            colors: "#7cb5ec #434348 #90ed7d #f7a35c #8085e9 #f15c80 #e4d354 #2b908f #f45b5b #91e8e1".split(" "),
            symbols: ["circle", "diamond", "square", "triangle", "triangle-down"],
            lang: {
                loading: "Loading...",
                months: "January February March April May June July August September October November December".split(" "),
                shortMonths: "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" "),
                weekdays: "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
                decimalPoint: ".",
                numericSymbols: "kMGTPE".split(""),
                resetZoom: "Reset zoom",
                resetZoomTitle: "Reset zoom level 1:1",
                thousandsSep: " "
            },
            global: {},
            time: m.defaultOptions,
            chart: {
                styledMode: !1,
                borderRadius: 0,
                colorCount: 10,
                defaultSeriesType: "line",
                ignoreHiddenSeries: !0,
                spacing: [10, 10, 15, 10],
                resetZoomButton: {
                    theme: {
                        zIndex: 6
                    },
                    position: {
                        align: "right",
                        x: -10,
                        y: 10
                    }
                },
                width: null,
                height: null,
                borderColor: "#335cad",
                backgroundColor: "#ffffff",
                plotBorderColor: "#cccccc"
            },
            title: {
                text: "Chart title",
                align: "center",
                margin: 15,
                widthAdjust: -44
            },
            subtitle: {
                text: "",
                align: "center",
                widthAdjust: -44
            },
            caption: {
                margin: 15,
                text: "",
                align: "left",
                verticalAlign: "bottom"
            },
            plotOptions: {},
            labels: {
                style: {
                    position: "absolute",
                    color: "#333333"
                }
            },
            legend: {
                enabled: !0,
                align: "center",
                alignColumns: !0,
                layout: "horizontal",
                labelFormatter: function() {
                    return this.name
                },
                borderColor: "#999999",
                borderRadius: 0,
                navigation: {
                    activeColor: "#003399",
                    inactiveColor: "#cccccc"
                },
                itemStyle: {
                    color: "#333333",
                    cursor: "pointer",
                    fontSize: "12px",
                    fontWeight: "bold",
                    textOverflow: "ellipsis"
                },
                itemHoverStyle: {
                    color: "#000000"
                },
                itemHiddenStyle: {
                    color: "#cccccc"
                },
                shadow: !1,
                itemCheckboxStyle: {
                    position: "absolute",
                    width: "13px",
                    height: "13px"
                },
                squareSymbol: !0,
                symbolPadding: 5,
                verticalAlign: "bottom",
                x: 0,
                y: 0,
                title: {
                    style: {
                        fontWeight: "bold"
                    }
                }
            },
            loading: {
                labelStyle: {
                    fontWeight: "bold",
                    position: "relative",
                    top: "45%"
                },
                style: {
                    position: "absolute",
                    backgroundColor: "#ffffff",
                    opacity: .5,
                    textAlign: "center"
                }
            },
            tooltip: {
                enabled: !0,
                animation: f.svg,
                borderRadius: 3,
                dateTimeLabelFormats: {
                    millisecond: "%A, %b %e, %H:%M:%S.%L",
                    second: "%A, %b %e, %H:%M:%S",
                    minute: "%A, %b %e, %H:%M",
                    hour: "%A, %b %e, %H:%M",
                    day: "%A, %b %e, %Y",
                    week: "Week from %A, %b %e, %Y",
                    month: "%B %Y",
                    year: "%Y"
                },
                footerFormat: "",
                padding: 8,
                snap: f.isTouchDevice ? 25 : 10,
                headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
                pointFormat: '<span style="color:{point.color}">\u25cf</span> {series.name}: <b>{point.y}</b><br/>',
                backgroundColor: J("#f7f7f7").setOpacity(.85).get(),
                borderWidth: 1,
                shadow: !0,
                style: {
                    color: "#333333",
                    cursor: "default",
                    fontSize: "12px",
                    whiteSpace: "nowrap"
                }
            },
            credits: {
                enabled: 0,
                href: "https://www.highcharts.com?credits",
                position: {
                    align: "right",
                    x: -10,
                    verticalAlign: "bottom",
                    y: -5
                },
                style: {
                    cursor: "pointer",
                    color: "#999999",
                    fontSize: "9px"
                },
                text: "Highcharts.com"
            }
        };
        f.setOptions = function(m) {
            f.defaultOptions = E(!0, f.defaultOptions, m); (m.time || m.global) && f.time.update(E(f.defaultOptions.global, f.defaultOptions.time, m.global, m.time));
            return f.defaultOptions
        };
        f.getOptions = function() {
            return f.defaultOptions
        };
        f.defaultPlotOptions = f.defaultOptions.plotOptions;
        f.time = new m(E(f.defaultOptions.global, f.defaultOptions.time));
        f.dateFormat = function(m, r, C) {
            return f.time.dateFormat(m, r, C)
        };
        ""
    });
    P(y, "parts/Axis.js", [y["parts/Globals.js"], y["parts/Color.js"], y["parts/Tick.js"], y["parts/Utilities.js"]],
    function(f, m, J, r) {
        var E = m.parse,
        N = r.addEvent,
        F = r.animObject,
        C = r.arrayMax,
        B = r.arrayMin,
        L = r.clamp,
        z = r.correctFloat,
        x = r.defined,
        A = r.destroyObjectProperties,
        t = r.error,
        n = r.extend,
        k = r.fireEvent,
        q = r.format,
        e = r.getMagnitude,
        c = r.isArray,
        g = r.isFunction,
        p = r.isNumber,
        b = r.isString,
        a = r.merge,
        v = r.normalizeTickInterval,
        D = r.objectEach,
        w = r.pick,
        l = r.relativeLength,
        h = r.removeEvent,
        u = r.splat,
        H = r.syncTimeout,
        M = f.defaultOptions,
        Q = f.deg2rad;
        m = function() {
            this.init.apply(this, arguments)
        };
        n(m.prototype, {
            defaultOptions: {
                dateTimeLabelFormats: {
                    millisecond: {
                        main: "%H:%M:%S.%L",
                        range: !1
                    },
                    second: {
                        main: "%H:%M:%S",
                        range: !1
                    },
                    minute: {
                        main: "%H:%M",
                        range: !1
                    },
                    hour: {
                        main: "%H:%M",
                        range: !1
                    },
                    day: {
                        main: "%e. %b"
                    },
                    week: {
                        main: "%e. %b"
                    },
                    month: {
                        main: "%b '%y"
                    },
                    year: {
                        main: "%Y"
                    }
                },
                endOnTick: !1,
                labels: {
                    enabled: !0,
                    indentation: 10,
                    x: 0,
                    style: {
                        color: "#666666",
                        cursor: "default",
                        fontSize: "11px"
                    }
                },
                maxPadding: .01,
                minorTickLength: 2,
                minorTickPosition: "outside",
                minPadding: .01,
                showEmpty: !0,
                startOfWeek: 1,
                startOnTick: !1,
                tickLength: 10,
                tickPixelInterval: 100,
                tickmarkPlacement: "between",
                tickPosition: "outside",
                title: {
                    align: "middle",
                    style: {
                        color: "#666666"
                    }
                },
                type: "linear",
                minorGridLineColor: "#f2f2f2",
                minorGridLineWidth: 1,
                minorTickColor: "#999999",
                lineColor: "#ccd6eb",
                lineWidth: 1,
                gridLineColor: "#e6e6e6",
                tickColor: "#ccd6eb"
            },
            defaultYAxisOptions: {
                endOnTick: !0,
                maxPadding: .05,
                minPadding: .05,
                tickPixelInterval: 72,
                showLastLabel: !0,
                labels: {
                    x: -8
                },
                startOnTick: !0,
                title: {
                    rotation: 270,
                    text: "Values"
                },
                stackLabels: {
                    allowOverlap: !1,
                    enabled: !1,
                    crop: !0,
                    overflow: "justify",
                    formatter: function() {
                        var a = this.axis.chart.numberFormatter;
                        return a(this.total, -1)
                    },
                    style: {
                        color: "#000000",
                        fontSize: "11px",
                        fontWeight: "bold",
                        textOutline: "1px contrast"
                    }
                },
                gridLineWidth: 1,
                lineWidth: 0
            },
            defaultLeftAxisOptions: {
                labels: {
                    x: -15
                },
                title: {
                    rotation: 270
                }
            },
            defaultRightAxisOptions: {
                labels: {
                    x: 15
                },
                title: {
                    rotation: 90
                }
            },
            defaultBottomAxisOptions: {
                labels: {
                    autoRotation: [ - 45],
                    x: 0
                },
                margin: 15,
                title: {
                    rotation: 0
                }
            },
            defaultTopAxisOptions: {
                labels: {
                    autoRotation: [ - 45],
                    x: 0
                },
                margin: 15,
                title: {
                    rotation: 0
                }
            },
            init: function(a, d) {
                var b = d.isX,
                c = this;
                c.chart = a;
                c.horiz = a.inverted && !c.isZAxis ? !b: b;
                c.isXAxis = b;
                c.coll = c.coll || (b ? "xAxis": "yAxis");
                k(this, "init", {
                    userOptions: d
                });
                c.opposite = d.opposite;
                c.side = d.side || (c.horiz ? c.opposite ? 0 : 2 : c.opposite ? 1 : 3);
                c.setOptions(d);
                var h = this.options,
                e = h.type;
                c.labelFormatter = h.labels.formatter || c.defaultLabelFormatter;
                c.userOptions = d;
                c.minPixelPadding = 0;
                c.reversed = h.reversed;
                c.visible = !1 !== h.visible;
                c.zoomEnabled = !1 !== h.zoomEnabled;
                c.hasNames = "category" === e || !0 === h.categories;
                c.categories = h.categories || c.hasNames;
                c.names || (c.names = [], c.names.keys = {});
                c.plotLinesAndBandsGroups = {};
                c.isLog = "logarithmic" === e;
                c.isDatetimeAxis = "datetime" === e;
                c.positiveValuesOnly = c.isLog && !c.allowNegativeLog;
                c.isLinked = x(h.linkedTo);
                c.ticks = {};
                c.labelEdge = [];
                c.minorTicks = {};
                c.plotLinesAndBands = [];
                c.alternateBands = {};
                c.len = 0;
                c.minRange = c.userMinRange = h.minRange || h.maxZoom;
                c.range = h.range;
                c.offset = h.offset || 0;
                c.stacks = {};
                c.oldStacks = {};
                c.stacksTouched = 0;
                c.max = null;
                c.min = null;
                c.crosshair = w(h.crosshair, u(a.options.tooltip.crosshairs)[b ? 0 : 1], !1);
                d = c.options.events; - 1 === a.axes.indexOf(c) && (b ? a.axes.splice(a.xAxis.length, 0, c) : a.axes.push(c), a[c.coll].push(c));
                c.series = c.series || [];
                a.inverted && !c.isZAxis && b && "undefined" === typeof c.reversed && (c.reversed = !0);
                D(d,
                function(d, a) {
                    g(d) && N(c, a, d)
                });
                c.lin2log = h.linearToLogConverter || c.lin2log;
                c.isLog && (c.val2lin = c.log2lin, c.lin2val = c.lin2log);
                k(this, "afterInit")
            },
            setOptions: function(b) {
                this.options = a(this.defaultOptions, "yAxis" === this.coll && this.defaultYAxisOptions, [this.defaultTopAxisOptions, this.defaultRightAxisOptions, this.defaultBottomAxisOptions, this.defaultLeftAxisOptions][this.side], a(M[this.coll], b));
                k(this, "afterSetOptions", {
                    userOptions: b
                })
            },
            defaultLabelFormatter: function() {
                var a = this.axis,
                d = this.value,
                b = a.chart.time,
                c = a.categories,
                h = this.dateTimeLabelFormat,
                e = M.lang,
                l = e.numericSymbols;
                e = e.numericSymbolMagnitude || 1E3;
                var g = l && l.length,
                p = a.options.labels.format;
                a = a.isLog ? Math.abs(d) : a.tickInterval;
                var u = this.chart,
                v = u.numberFormatter;
                if (p) var k = q(p, this, u);
                else if (c) k = d;
                else if (h) k = b.dateFormat(h, d);
                else if (g && 1E3 <= a) for (; g--&&"undefined" === typeof k;) b = Math.pow(e, g + 1),
                a >= b && 0 === 10 * d % b && null !== l[g] && 0 !== d && (k = v(d / b, -1) + l[g]);
                "undefined" === typeof k && (k = 1E4 <= Math.abs(d) ? v(d, -1) : v(d, -1, void 0, ""));
                return k
            },
            getSeriesExtremes: function() {
                var a = this,
                d = a.chart,
                b;
                k(this, "getSeriesExtremes", null,
                function() {
                    a.hasVisibleSeries = !1;
                    a.dataMin = a.dataMax = a.threshold = null;
                    a.softThreshold = !a.isXAxis;
                    a.buildStacks && a.buildStacks();
                    a.series.forEach(function(c) {
                        if (c.visible || !d.options.chart.ignoreHiddenSeries) {
                            var h = c.options,
                            e = h.threshold;
                            a.hasVisibleSeries = !0;
                            a.positiveValuesOnly && 0 >= e && (e = null);
                            if (a.isXAxis) {
                                if (h = c.xData, h.length) {
                                    b = c.getXExtremes(h);
                                    var l = b.min;
                                    var g = b.max;
                                    p(l) || l instanceof Date || (h = h.filter(p), b = c.getXExtremes(h), l = b.min, g = b.max);
                                    h.length && (a.dataMin = Math.min(w(a.dataMin, l), l), a.dataMax = Math.max(w(a.dataMax, g), g))
                                }
                            } else if (c.getExtremes(), g = c.dataMax, l = c.dataMin, x(l) && x(g) && (a.dataMin = Math.min(w(a.dataMin, l), l), a.dataMax = Math.max(w(a.dataMax, g), g)), x(e) && (a.threshold = e), !h.softThreshold || a.positiveValuesOnly) a.softThreshold = !1
                        }
                    })
                });
                k(this, "afterGetSeriesExtremes")
            },
            translate: function(a, d, b, c, h, e) {
                var l = this.linkedParent || this,
                g = 1,
                G = 0,
                I = c ? l.oldTransA: l.transA;
                c = c ? l.oldMin: l.min;
                var u = l.minPixelPadding;
                h = (l.isOrdinal || l.isBroken || l.isLog && h) && l.lin2val;
                I || (I = l.transA);
                b && (g *= -1, G = l.len);
                l.reversed && (g *= -1, G -= g * (l.sector || l.len));
                d ? (a = (a * g + G - u) / I + c, h && (a = l.lin2val(a))) : (h && (a = l.val2lin(a)), a = p(c) ? g * (a - c) * I + G + g * u + (p(e) ? I * e: 0) : void 0);
                return a
            },
            toPixels: function(a, d) {
                return this.translate(a, !1, !this.horiz, null, !0) + (d ? 0 : this.pos)
            },
            toValue: function(a, d) {
                return this.translate(a - (d ? 0 : this.pos), !0, !this.horiz, null, !0)
            },
            getPlotLinePath: function(a) {
                var d = this,
                b = d.chart,
                c = d.left,
                h = d.top,
                e = a.old,
                l = a.value,
                g = a.translatedValue,
                G = a.lineWidth,
                u = a.force,
                v, D, H, n, q = e && b.oldChartHeight || b.chartHeight,
                f = e && b.oldChartWidth || b.chartWidth,
                M, t = d.transB,
                x = function(d, a, b) {
                    if ("pass" !== u && d < a || d > b) u ? d = L(d, a, b) : M = !0;
                    return d
                };
                a = {
                    value: l,
                    lineWidth: G,
                    old: e,
                    force: u,
                    acrossPanes: a.acrossPanes,
                    translatedValue: g
                };
                k(this, "getPlotLinePath", a,
                function(a) {
                    g = w(g, d.translate(l, null, null, e));
                    g = L(g, -1E5, 1E5);
                    v = H = Math.round(g + t);
                    D = n = Math.round(q - g - t);
                    p(g) ? d.horiz ? (D = h, n = q - d.bottom, v = H = x(v, c, c + d.width)) : (v = c, H = f - d.right, D = n = x(D, h, h + d.height)) : (M = !0, u = !1);
                    a.path = M && !u ? null: b.renderer.crispLine(["M", v, D, "L", H, n], G || 1)
                });
                return a.path
            },
            getLinearTickPositions: function(a, d, b) {
                var c = z(Math.floor(d / a) * a);
                b = z(Math.ceil(b / a) * a);
                var h = [],
                e;
                z(c + a) === c && (e = 20);
                if (this.single) return [d];
                for (d = c; d <= b;) {
                    h.push(d);
                    d = z(d + a, e);
                    if (d === l) break;
                    var l = d
                }
                return h
            },
            getMinorTickInterval: function() {
                var a = this.options;
                return ! 0 === a.minorTicks ? w(a.minorTickInterval, "auto") : !1 === a.minorTicks ? null: a.minorTickInterval
            },
            getMinorTickPositions: function() {
                var a = this,
                d = a.options,
                b = a.tickPositions,
                c = a.minorTickInterval,
                h = [],
                e = a.pointRangePadding || 0,
                l = a.min - e;
                e = a.max + e;
                var g = e - l;
                if (g && g / c < a.len / 3) if (a.isLog) this.paddedTicks.forEach(function(d, b, e) {
                    b && h.push.apply(h, a.getLogTickPositions(c, e[b - 1], e[b], !0))
                });
                else if (a.isDatetimeAxis && "auto" === this.getMinorTickInterval()) h = h.concat(a.getTimeTicks(a.normalizeTimeTickInterval(c), l, e, d.startOfWeek));
                else for (d = l + (b[0] - l) % c; d <= e && d !== h[0]; d += c) h.push(d);
                0 !== h.length && a.trimTicks(h);
                return h
            },
            adjustForMinRange: function() {
                var a = this.options,
                d = this.min,
                b = this.max,
                c, h, e, l, g;
                this.isXAxis && "undefined" === typeof this.minRange && !this.isLog && (x(a.min) || x(a.max) ? this.minRange = null: (this.series.forEach(function(d) {
                    l = d.xData;
                    for (h = g = d.xIncrement ? 1 : l.length - 1; 0 < h; h--) if (e = l[h] - l[h - 1], "undefined" === typeof c || e < c) c = e
                }), this.minRange = Math.min(5 * c, this.dataMax - this.dataMin)));
                if (b - d < this.minRange) {
                    var p = this.dataMax - this.dataMin >= this.minRange;
                    var u = this.minRange;
                    var v = (u - b + d) / 2;
                    v = [d - v, w(a.min, d - v)];
                    p && (v[2] = this.isLog ? this.log2lin(this.dataMin) : this.dataMin);
                    d = C(v);
                    b = [d + u, w(a.max, d + u)];
                    p && (b[2] = this.isLog ? this.log2lin(this.dataMax) : this.dataMax);
                    b = B(b);
                    b - d < u && (v[0] = b - u, v[1] = w(a.min, b - u), d = C(v))
                }
                this.min = d;
                this.max = b
            },
            getClosest: function() {
                var a;
                this.categories ? a = 1 : this.series.forEach(function(d) {
                    var b = d.closestPointRange,
                    c = d.visible || !d.chart.options.chart.ignoreHiddenSeries; ! d.noSharedTooltip && x(b) && c && (a = x(a) ? Math.min(a, b) : b)
                });
                return a
            },
            nameToX: function(a) {
                var d = c(this.categories),
                b = d ? this.categories: this.names,
                h = a.options.x;
                a.series.requireSorting = !1;
                x(h) || (h = !1 === this.options.uniqueNames ? a.series.autoIncrement() : d ? b.indexOf(a.name) : w(b.keys[a.name], -1));
                if ( - 1 === h) {
                    if (!d) var e = b.length
                } else e = h;
                "undefined" !== typeof e && (this.names[e] = a.name, this.names.keys[a.name] = e);
                return e
            },
            updateNames: function() {
                var a = this,
                d = this.names;
                0 < d.length && (Object.keys(d.keys).forEach(function(a) {
                    delete d.keys[a]
                }), d.length = 0, this.minRange = this.userMinRange, (this.series || []).forEach(function(d) {
                    d.xIncrement = null;
                    if (!d.points || d.isDirtyData) a.max = Math.max(a.max, d.xData.length - 1),
                    d.processData(),
                    d.generatePoints();
                    d.data.forEach(function(b, c) {
                        if (b && b.options && "undefined" !== typeof b.name) {
                            var h = a.nameToX(b);
                            "undefined" !== typeof h && h !== b.x && (b.x = h, d.xData[c] = h)
                        }
                    })
                }))
            },
            setAxisTranslation: function(a) {
                var d = this,
                c = d.max - d.min,
                h = d.axisPointRange || 0,
                e = 0,
                l = 0,
                g = d.linkedParent,
                p = !!d.categories,
                u = d.transA,
                G = d.isXAxis;
                if (G || p || h) {
                    var v = d.getClosest();
                    g ? (e = g.minPointOffset, l = g.pointRangePadding) : d.series.forEach(function(a) {
                        var c = p ? 1 : G ? w(a.options.pointRange, v, 0) : d.axisPointRange || 0,
                        g = a.options.pointPlacement;
                        h = Math.max(h, c);
                        if (!d.single || p) a = a.is("xrange") ? !G: G,
                        e = Math.max(e, a && b(g) ? 0 : c / 2),
                        l = Math.max(l, a && "on" === g ? 0 : c)
                    });
                    g = d.ordinalSlope && v ? d.ordinalSlope / v: 1;
                    d.minPointOffset = e *= g;
                    d.pointRangePadding = l *= g;
                    d.pointRange = Math.min(h, d.single && p ? 1 : c);
                    G && (d.closestPointRange = v)
                }
                a && (d.oldTransA = u);
                d.translationSlope = d.transA = u = d.staticScale || d.len / (c + l || 1);
                d.transB = d.horiz ? d.left: d.bottom;
                d.minPixelPadding = u * e;
                k(this, "afterSetAxisTranslation")
            },
            minFromRange: function() {
                return this.max - this.range
            },
            setTickInterval: function(a) {
                var d = this,
                b = d.chart,
                c = d.options,
                h = d.isLog,
                l = d.isDatetimeAxis,
                g = d.isXAxis,
                u = d.isLinked,
                G = c.maxPadding,
                D = c.minPadding,
                H = c.tickInterval,
                n = c.tickPixelInterval,
                q = d.categories,
                f = p(d.threshold) ? d.threshold: null,
                M = d.softThreshold;
                l || q || u || this.getTickAmount();
                var Q = w(d.userMin, c.min);
                var m = w(d.userMax, c.max);
                if (u) {
                    d.linkedParent = b[d.coll][c.linkedTo];
                    var A = d.linkedParent.getExtremes();
                    d.min = w(A.min, A.dataMin);
                    d.max = w(A.max, A.dataMax);
                    c.type !== d.linkedParent.options.type && t(11, 1, b)
                } else {
                    if (!M && x(f)) if (d.dataMin >= f) A = f,
                    D = 0;
                    else if (d.dataMax <= f) {
                        var r = f;
                        G = 0
                    }
                    d.min = w(Q, A, d.dataMin);
                    d.max = w(m, r, d.dataMax)
                }
                h && (d.positiveValuesOnly && !a && 0 >= Math.min(d.min, w(d.dataMin, d.min)) && t(10, 1, b), d.min = z(d.log2lin(d.min), 16), d.max = z(d.log2lin(d.max), 16));
                d.range && x(d.max) && (d.userMin = d.min = Q = Math.max(d.dataMin, d.minFromRange()), d.userMax = m = d.max, d.range = null);
                k(d, "foundExtremes");
                d.beforePadding && d.beforePadding();
                d.adjustForMinRange(); ! (q || d.axisPointRange || d.usePercentage || u) && x(d.min) && x(d.max) && (b = d.max - d.min) && (!x(Q) && D && (d.min -= b * D), !x(m) && G && (d.max += b * G));
                p(d.userMin) || (p(c.softMin) && c.softMin < d.min && (d.min = Q = c.softMin), p(c.floor) && (d.min = Math.max(d.min, c.floor)));
                p(d.userMax) || (p(c.softMax) && c.softMax > d.max && (d.max = m = c.softMax), p(c.ceiling) && (d.max = Math.min(d.max, c.ceiling)));
                M && x(d.dataMin) && (f = f || 0, !x(Q) && d.min < f && d.dataMin >= f ? d.min = d.options.minRange ? Math.min(f, d.max - d.minRange) : f: !x(m) && d.max > f && d.dataMax <= f && (d.max = d.options.minRange ? Math.max(f, d.min + d.minRange) : f));
                d.tickInterval = d.min === d.max || "undefined" === typeof d.min || "undefined" === typeof d.max ? 1 : u && !H && n === d.linkedParent.options.tickPixelInterval ? H = d.linkedParent.tickInterval: w(H, this.tickAmount ? (d.max - d.min) / Math.max(this.tickAmount - 1, 1) : void 0, q ? 1 : (d.max - d.min) * n / Math.max(d.len, n));
                g && !a && d.series.forEach(function(a) {
                    a.processData(d.min !== d.oldMin || d.max !== d.oldMax)
                });
                d.setAxisTranslation(!0);
                d.beforeSetTickPositions && d.beforeSetTickPositions();
                d.postProcessTickInterval && (d.tickInterval = d.postProcessTickInterval(d.tickInterval));
                d.pointRange && !H && (d.tickInterval = Math.max(d.pointRange, d.tickInterval));
                a = w(c.minTickInterval, d.isDatetimeAxis && d.closestPointRange); ! H && d.tickInterval < a && (d.tickInterval = a);
                l || h || H || (d.tickInterval = v(d.tickInterval, null, e(d.tickInterval), w(c.allowDecimals, !(.5 < d.tickInterval && 5 > d.tickInterval && 1E3 < d.max && 9999 > d.max)), !!this.tickAmount));
                this.tickAmount || (d.tickInterval = d.unsquish());
                this.setTickPositions()
            },
            setTickPositions: function() {
                var a = this.options,
                d = a.tickPositions;
                var b = this.getMinorTickInterval();
                var c = a.tickPositioner,
                h = a.startOnTick,
                e = a.endOnTick;
                this.tickmarkOffset = this.categories && "between" === a.tickmarkPlacement && 1 === this.tickInterval ? .5 : 0;
                this.minorTickInterval = "auto" === b && this.tickInterval ? this.tickInterval / 5 : b;
                this.single = this.min === this.max && x(this.min) && !this.tickAmount && (parseInt(this.min, 10) === this.min || !1 !== a.allowDecimals);
                this.tickPositions = b = d && d.slice(); ! b && (!this.ordinalPositions && (this.max - this.min) / this.tickInterval > Math.max(2 * this.len, 200) ? (b = [this.min, this.max], t(19, !1, this.chart)) : b = this.isDatetimeAxis ? this.getTimeTicks(this.normalizeTimeTickInterval(this.tickInterval, a.units), this.min, this.max, a.startOfWeek, this.ordinalPositions, this.closestPointRange, !0) : this.isLog ? this.getLogTickPositions(this.tickInterval, this.min, this.max) : this.getLinearTickPositions(this.tickInterval, this.min, this.max), b.length > this.len && (b = [b[0], b.pop()], b[0] === b[1] && (b.length = 1)), this.tickPositions = b, c && (c = c.apply(this, [this.min, this.max]))) && (this.tickPositions = b = c);
                this.paddedTicks = b.slice(0);
                this.trimTicks(b, h, e);
                this.isLinked || (this.single && 2 > b.length && !this.categories && !this.series.some(function(d) {
                    return d.is("heatmap") && "between" === d.options.pointPlacement
                }) && (this.min -= .5, this.max += .5), d || c || this.adjustTickAmount());
                k(this, "afterSetTickPositions")
            },
            trimTicks: function(a, d, b) {
                var c = a[0],
                h = a[a.length - 1],
                e = !this.isOrdinal && this.minPointOffset || 0;
                k(this, "trimTicks");
                if (!this.isLinked) {
                    if (d && -Infinity !== c) this.min = c;
                    else for (; this.min - e > a[0];) a.shift();
                    if (b) this.max = h;
                    else for (; this.max + e < a[a.length - 1];) a.pop();
                    0 === a.length && x(c) && !this.options.tickPositions && a.push((h + c) / 2)
                }
            },
            alignToOthers: function() {
                var a = {},
                d, b = this.options; ! 1 === this.chart.options.chart.alignTicks || !1 === b.alignTicks || !1 === b.startOnTick || !1 === b.endOnTick || this.isLog || this.chart[this.coll].forEach(function(b) {
                    var c = b.options;
                    c = [b.horiz ? c.left: c.top, c.width, c.height, c.pane].join();
                    b.series.length && (a[c] ? d = !0 : a[c] = 1)
                });
                return d
            },
            getTickAmount: function() {
                var a = this.options,
                d = a.tickAmount,
                b = a.tickPixelInterval; ! x(a.tickInterval) && this.len < b && !this.isRadial && !this.isLog && a.startOnTick && a.endOnTick && (d = 2); ! d && this.alignToOthers() && (d = Math.ceil(this.len / b) + 1);
                4 > d && (this.finalTickAmt = d, d = 5);
                this.tickAmount = d
            },
            adjustTickAmount: function() {
                var a = this.options,
                d = this.tickInterval,
                b = this.tickPositions,
                c = this.tickAmount,
                h = this.finalTickAmt,
                e = b && b.length,
                l = w(this.threshold, this.softThreshold ? 0 : null),
                g;
                if (this.hasData()) {
                    if (e < c) {
                        for (g = this.min; b.length < c;) b.length % 2 || g === l ? b.push(z(b[b.length - 1] + d)) : b.unshift(z(b[0] - d));
                        this.transA *= (e - 1) / (c - 1);
                        this.min = a.startOnTick ? b[0] : Math.min(this.min, b[0]);
                        this.max = a.endOnTick ? b[b.length - 1] : Math.max(this.max, b[b.length - 1])
                    } else e > c && (this.tickInterval *= 2, this.setTickPositions());
                    if (x(h)) {
                        for (d = a = b.length; d--;)(3 === h && 1 === d % 2 || 2 >= h && 0 < d && d < a - 1) && b.splice(d, 1);
                        this.finalTickAmt = void 0
                    }
                }
            },
            setScale: function() {
                var a = this.series.some(function(d) {
                    return d.isDirtyData || d.isDirty || d.xAxis && d.xAxis.isDirty
                }),
                d;
                this.oldMin = this.min;
                this.oldMax = this.max;
                this.oldAxisLength = this.len;
                this.setAxisSize(); (d = this.len !== this.oldAxisLength) || a || this.isLinked || this.forceRedraw || this.userMin !== this.oldUserMin || this.userMax !== this.oldUserMax || this.alignToOthers() ? (this.resetStacks && this.resetStacks(), this.forceRedraw = !1, this.getSeriesExtremes(), this.setTickInterval(), this.oldUserMin = this.userMin, this.oldUserMax = this.userMax, this.isDirty || (this.isDirty = d || this.min !== this.oldMin || this.max !== this.oldMax)) : this.cleanStacks && this.cleanStacks();
                k(this, "afterSetScale")
            },
            setExtremes: function(a, d, b, c, h) {
                var e = this,
                l = e.chart;
                b = w(b, !0);
                e.series.forEach(function(d) {
                    delete d.kdTree
                });
                h = n(h, {
                    min: a,
                    max: d
                });
                k(e, "setExtremes", h,
                function() {
                    e.userMin = a;
                    e.userMax = d;
                    e.eventArgs = h;
                    b && l.redraw(c)
                })
            },
            zoom: function(a, d) {
                var b = this.dataMin,
                c = this.dataMax,
                h = this.options,
                e = Math.min(b, w(h.min, b)),
                l = Math.max(c, w(h.max, c));
                a = {
                    newMin: a,
                    newMax: d
                };
                k(this, "zoom", a,
                function(d) {
                    var a = d.newMin,
                    h = d.newMax;
                    if (a !== this.min || h !== this.max) this.allowZoomOutside || (x(b) && (a < e && (a = e), a > l && (a = l)), x(c) && (h < e && (h = e), h > l && (h = l))),
                    this.displayBtn = "undefined" !== typeof a || "undefined" !== typeof h,
                    this.setExtremes(a, h, !1, void 0, {
                        trigger: "zoom"
                    });
                    d.zoomed = !0
                });
                return a.zoomed
            },
            setAxisSize: function() {
                var a = this.chart,
                d = this.options,
                b = d.offsets || [0, 0, 0, 0],
                c = this.horiz,
                h = this.width = Math.round(l(w(d.width, a.plotWidth - b[3] + b[1]), a.plotWidth)),
                e = this.height = Math.round(l(w(d.height, a.plotHeight - b[0] + b[2]), a.plotHeight)),
                g = this.top = Math.round(l(w(d.top, a.plotTop + b[0]), a.plotHeight, a.plotTop));
                d = this.left = Math.round(l(w(d.left, a.plotLeft + b[3]), a.plotWidth, a.plotLeft));
                this.bottom = a.chartHeight - e - g;
                this.right = a.chartWidth - h - d;
                this.len = Math.max(c ? h: e, 0);
                this.pos = c ? d: g
            },
            getExtremes: function() {
                var a = this.isLog;
                return {
                    min: a ? z(this.lin2log(this.min)) : this.min,
                    max: a ? z(this.lin2log(this.max)) : this.max,
                    dataMin: this.dataMin,
                    dataMax: this.dataMax,
                    userMin: this.userMin,
                    userMax: this.userMax
                }
            },
            getThreshold: function(a) {
                var d = this.isLog,
                b = d ? this.lin2log(this.min) : this.min;
                d = d ? this.lin2log(this.max) : this.max;
                null === a || -Infinity === a ? a = b: Infinity === a ? a = d: b > a ? a = b: d < a && (a = d);
                return this.translate(a, 0, 1, 0, 1)
            },
            autoLabelAlign: function(a) {
                var d = (w(a, 0) - 90 * this.side + 720) % 360;
                a = {
                    align: "center"
                };
                k(this, "autoLabelAlign", a,
                function(a) {
                    15 < d && 165 > d ? a.align = "right": 195 < d && 345 > d && (a.align = "left")
                });
                return a.align
            },
            tickSize: function(a) {
                var d = this.options,
                b = d[a + "Length"],
                c = w(d[a + "Width"], "tick" === a && this.isXAxis && !this.categories ? 1 : 0);
                if (c && b) {
                    "inside" === d[a + "Position"] && (b = -b);
                    var h = [b, c]
                }
                a = {
                    tickSize: h
                };
                k(this, "afterTickSize", a);
                return a.tickSize
            },
            labelMetrics: function() {
                var a = this.tickPositions && this.tickPositions[0] || 0;
                return this.chart.renderer.fontMetrics(this.options.labels.style && this.options.labels.style.fontSize, this.ticks[a] && this.ticks[a].label)
            },
            unsquish: function() {
                var a = this.options.labels,
                d = this.horiz,
                b = this.tickInterval,
                c = b,
                h = this.len / (((this.categories ? 1 : 0) + this.max - this.min) / b),
                e,
                l = a.rotation,
                g = this.labelMetrics(),
                p,
                u = Number.MAX_VALUE,
                v,
                k = this.max - this.min,
                D = function(d) {
                    var a = d / (h || 1);
                    a = 1 < a ? Math.ceil(a) : 1;
                    a * b > k && Infinity !== d && Infinity !== h && k && (a = Math.ceil(k / b));
                    return z(a * b)
                };
                d ? (v = !a.staggerLines && !a.step && (x(l) ? [l] : h < w(a.autoRotationLimit, 80) && a.autoRotation)) && v.forEach(function(d) {
                    if (d === l || d && -90 <= d && 90 >= d) {
                        p = D(Math.abs(g.h / Math.sin(Q * d)));
                        var a = p + Math.abs(d / 360);
                        a < u && (u = a, e = d, c = p)
                    }
                }) : a.step || (c = D(g.h));
                this.autoRotation = v;
                this.labelRotation = w(e, l);
                return c
            },
            getSlotWidth: function(a) {
                var d = this.chart,
                b = this.horiz,
                c = this.options.labels,
                h = Math.max(this.tickPositions.length - (this.categories ? 0 : 1), 1),
                e = d.margin[3];
                return a && a.slotWidth || b && 2 > (c.step || 0) && !c.rotation && (this.staggerLines || 1) * this.len / h || !b && (c.style && parseInt(c.style.width, 10) || e && e - d.spacing[3] || .33 * d.chartWidth)
            },
            renderUnsquish: function() {
                var a = this.chart,
                d = a.renderer,
                c = this.tickPositions,
                h = this.ticks,
                e = this.options.labels,
                l = e && e.style || {},
                g = this.horiz,
                p = this.getSlotWidth(),
                u = Math.max(1, Math.round(p - 2 * (e.padding || 5))),
                v = {},
                k = this.labelMetrics(),
                w = e.style && e.style.textOverflow,
                D = 0;
                b(e.rotation) || (v.rotation = e.rotation || 0);
                c.forEach(function(d) {
                    d = h[d];
                    d.movedLabel && d.replaceMovedLabel();
                    d && d.label && d.label.textPxLength > D && (D = d.label.textPxLength)
                });
                this.maxLabelLength = D;
                if (this.autoRotation) D > u && D > k.h ? v.rotation = this.labelRotation: this.labelRotation = 0;
                else if (p) {
                    var H = u;
                    if (!w) {
                        var n = "clip";
                        for (u = c.length; ! g && u--;) {
                            var q = c[u];
                            if (q = h[q].label) q.styles && "ellipsis" === q.styles.textOverflow ? q.css({
                                textOverflow: "clip"
                            }) : q.textPxLength > p && q.css({
                                width: p + "px"
                            }),
                            q.getBBox().height > this.len / c.length - (k.h - k.f) && (q.specificTextOverflow = "ellipsis")
                        }
                    }
                }
                v.rotation && (H = D > .5 * a.chartHeight ? .33 * a.chartHeight: D, w || (n = "ellipsis"));
                if (this.labelAlign = e.align || this.autoLabelAlign(this.labelRotation)) v.align = this.labelAlign;
                c.forEach(function(d) {
                    var a = (d = h[d]) && d.label,
                    b = l.width,
                    c = {};
                    a && (a.attr(v), d.shortenLabel ? d.shortenLabel() : H && !b && "nowrap" !== l.whiteSpace && (H < a.textPxLength || "SPAN" === a.element.tagName) ? (c.width = H, w || (c.textOverflow = a.specificTextOverflow || n), a.css(c)) : a.styles && a.styles.width && !c.width && !b && a.css({
                        width: null
                    }), delete a.specificTextOverflow, d.rotation = v.rotation)
                },
                this);
                this.tickRotCorr = d.rotCorr(k.b, this.labelRotation || 0, 0 !== this.side)
            },
            hasData: function() {
                return this.series.some(function(a) {
                    return a.hasData()
                }) || this.options.showEmpty && x(this.min) && x(this.max)
            },
            addTitle: function(b) {
                var d = this.chart.renderer,
                c = this.horiz,
                h = this.opposite,
                e = this.options.title,
                l, g = this.chart.styledMode;
                this.axisTitle || ((l = e.textAlign) || (l = (c ? {
                    low: "left",
                    middle: "center",
                    high: "right"
                }: {
                    low: h ? "right": "left",
                    middle: "center",
                    high: h ? "left": "right"
                })[e.align]), this.axisTitle = d.text(e.text, 0, 0, e.useHTML).attr({
                    zIndex: 7,
                    rotation: e.rotation || 0,
                    align: l
                }).addClass("highcharts-axis-title"), g || this.axisTitle.css(a(e.style)), this.axisTitle.add(this.axisGroup), this.axisTitle.isNew = !0);
                g || e.style.width || this.isRadial || this.axisTitle.css({
                    width: this.len
                });
                this.axisTitle[b ? "show": "hide"](b)
            },
            generateTick: function(a) {
                var d = this.ticks;
                d[a] ? d[a].addLabel() : d[a] = new J(this, a)
            },
            getOffset: function() {
                var a = this,
                d = a.chart,
                b = d.renderer,
                c = a.options,
                h = a.tickPositions,
                e = a.ticks,
                l = a.horiz,
                g = a.side,
                p = d.inverted && !a.isZAxis ? [1, 0, 3, 2][g] : g,
                u,
                v = 0,
                H = 0,
                n = c.title,
                q = c.labels,
                f = 0,
                M = d.axisOffset;
                d = d.clipOffset;
                var t = [ - 1, 1, 1, -1][g],
                Q = c.className,
                z = a.axisParent;
                var m = a.hasData();
                a.showAxis = u = m || w(c.showEmpty, !0);
                a.staggerLines = a.horiz && q.staggerLines;
                a.axisGroup || (a.gridGroup = b.g("grid").attr({
                    zIndex: c.gridZIndex || 1
                }).addClass("highcharts-" + this.coll.toLowerCase() + "-grid " + (Q || "")).add(z), a.axisGroup = b.g("axis").attr({
                    zIndex: c.zIndex || 2
                }).addClass("highcharts-" + this.coll.toLowerCase() + " " + (Q || "")).add(z), a.labelGroup = b.g("axis-labels").attr({
                    zIndex: q.zIndex || 7
                }).addClass("highcharts-" + a.coll.toLowerCase() + "-labels " + (Q || "")).add(z));
                m || a.isLinked ? (h.forEach(function(d, b) {
                    a.generateTick(d, b)
                }), a.renderUnsquish(), a.reserveSpaceDefault = 0 === g || 2 === g || {
                    1 : "left",
                    3 : "right"
                } [g] === a.labelAlign, w(q.reserveSpace, "center" === a.labelAlign ? !0 : null, a.reserveSpaceDefault) && h.forEach(function(d) {
                    f = Math.max(e[d].getLabelSize(), f)
                }), a.staggerLines && (f *= a.staggerLines), a.labelOffset = f * (a.opposite ? -1 : 1)) : D(e,
                function(d, a) {
                    d.destroy();
                    delete e[a]
                });
                if (n && n.text && !1 !== n.enabled && (a.addTitle(u), u && !1 !== n.reserveSpace)) {
                    a.titleOffset = v = a.axisTitle.getBBox()[l ? "height": "width"];
                    var A = n.offset;
                    H = x(A) ? 0 : w(n.margin, l ? 5 : 10)
                }
                a.renderLine();
                a.offset = t * w(c.offset, M[g] ? M[g] + (c.margin || 0) : 0);
                a.tickRotCorr = a.tickRotCorr || {
                    x: 0,
                    y: 0
                };
                b = 0 === g ? -a.labelMetrics().h: 2 === g ? a.tickRotCorr.y: 0;
                H = Math.abs(f) + H;
                f && (H = H - b + t * (l ? w(q.y, a.tickRotCorr.y + 8 * t) : q.x));
                a.axisTitleMargin = w(A, H);
                a.getMaxLabelDimensions && (a.maxLabelDimensions = a.getMaxLabelDimensions(e, h));
                l = this.tickSize("tick");
                M[g] = Math.max(M[g], a.axisTitleMargin + v + t * a.offset, H, h && h.length && l ? l[0] + t * a.offset: 0);
                c = c.offset ? 0 : 2 * Math.floor(a.axisLine.strokeWidth() / 2);
                d[p] = Math.max(d[p], c);
                k(this, "afterGetOffset")
            },
            getLinePath: function(a) {
                var d = this.chart,
                b = this.opposite,
                c = this.offset,
                h = this.horiz,
                e = this.left + (b ? this.width: 0) + c;
                c = d.chartHeight - this.bottom - (b ? this.height: 0) + c;
                b && (a *= -1);
                return d.renderer.crispLine(["M", h ? this.left: e, h ? c: this.top, "L", h ? d.chartWidth - this.right: e, h ? c: d.chartHeight - this.bottom], a)
            },
            renderLine: function() {
                this.axisLine || (this.axisLine = this.chart.renderer.path().addClass("highcharts-axis-line").add(this.axisGroup), this.chart.styledMode || this.axisLine.attr({
                    stroke: this.options.lineColor,
                    "stroke-width": this.options.lineWidth,
                    zIndex: 7
                }))
            },
            getTitlePosition: function() {
                var a = this.horiz,
                d = this.left,
                b = this.top,
                c = this.len,
                h = this.options.title,
                e = a ? d: b,
                l = this.opposite,
                g = this.offset,
                p = h.x || 0,
                u = h.y || 0,
                v = this.axisTitle,
                w = this.chart.renderer.fontMetrics(h.style && h.style.fontSize, v);
                v = Math.max(v.getBBox(null, 0).height - w.h - 1, 0);
                c = {
                    low: e + (a ? 0 : c),
                    middle: e + c / 2,
                    high: e + (a ? c: 0)
                } [h.align];
                d = (a ? b + this.height: d) + (a ? 1 : -1) * (l ? -1 : 1) * this.axisTitleMargin + [ - v, v, w.f, -v][this.side];
                a = {
                    x: a ? c + p: d + (l ? this.width: 0) + g + p,
                    y: a ? d + u - (l ? this.height: 0) + g: c + u
                };
                k(this, "afterGetTitlePosition", {
                    titlePosition: a
                });
                return a
            },
            renderMinorTick: function(a) {
                var d = this.chart.hasRendered && p(this.oldMin),
                b = this.minorTicks;
                b[a] || (b[a] = new J(this, a, "minor"));
                d && b[a].isNew && b[a].render(null, !0);
                b[a].render(null, !1, 1)
            },
            renderTick: function(a, d) {
                var b = this.isLinked,
                c = this.ticks,
                h = this.chart.hasRendered && p(this.oldMin);
                if (!b || a >= this.min && a <= this.max) c[a] || (c[a] = new J(this, a)),
                h && c[a].isNew && c[a].render(d, !0, -1),
                c[a].render(d)
            },
            render: function() {
                var a = this,
                d = a.chart,
                b = a.options,
                c = a.isLog,
                h = a.isLinked,
                e = a.tickPositions,
                l = a.axisTitle,
                g = a.ticks,
                u = a.minorTicks,
                v = a.alternateBands,
                w = b.stackLabels,
                n = b.alternateGridColor,
                q = a.tickmarkOffset,
                M = a.axisLine,
                t = a.showAxis,
                x = F(d.renderer.globalAnimation),
                Q,
                z;
                a.labelEdge.length = 0;
                a.overlap = !1; [g, u, v].forEach(function(d) {
                    D(d,
                    function(d) {
                        d.isActive = !1
                    })
                });
                if (a.hasData() || h) a.minorTickInterval && !a.categories && a.getMinorTickPositions().forEach(function(d) {
                    a.renderMinorTick(d)
                }),
                e.length && (e.forEach(function(d, b) {
                    a.renderTick(d, b)
                }), q && (0 === a.min || a.single) && (g[ - 1] || (g[ - 1] = new J(a, -1, null, !0)), g[ - 1].render( - 1))),
                n && e.forEach(function(b, h) {
                    z = "undefined" !== typeof e[h + 1] ? e[h + 1] + q: a.max - q;
                    0 === h % 2 && b < a.max && z <= a.max + (d.polar ? -q: q) && (v[b] || (v[b] = new f.PlotLineOrBand(a)), Q = b + q, v[b].options = {
                        from: c ? a.lin2log(Q) : Q,
                        to: c ? a.lin2log(z) : z,
                        color: n
                    },
                    v[b].render(), v[b].isActive = !0)
                }),
                a._addedPlotLB || ((b.plotLines || []).concat(b.plotBands || []).forEach(function(d) {
                    a.addPlotBandOrLine(d)
                }), a._addedPlotLB = !0); [g, u, v].forEach(function(a) {
                    var b, c = [],
                    h = x.duration;
                    D(a,
                    function(d, a) {
                        d.isActive || (d.render(a, !1, 0), d.isActive = !1, c.push(a))
                    });
                    H(function() {
                        for (b = c.length; b--;) a[c[b]] && !a[c[b]].isActive && (a[c[b]].destroy(), delete a[c[b]])
                    },
                    a !== v && d.hasRendered && h ? h: 0)
                });
                M && (M[M.isPlaced ? "animate": "attr"]({
                    d: this.getLinePath(M.strokeWidth())
                }), M.isPlaced = !0, M[t ? "show": "hide"](t));
                l && t && (b = a.getTitlePosition(), p(b.y) ? (l[l.isNew ? "attr": "animate"](b), l.isNew = !1) : (l.attr("y", -9999), l.isNew = !0));
                w && w.enabled && a.renderStackTotals();
                a.isDirty = !1;
                k(this, "afterRender")
            },
            redraw: function() {
                this.visible && (this.render(), this.plotLinesAndBands.forEach(function(a) {
                    a.render()
                }));
                this.series.forEach(function(a) {
                    a.isDirty = !0
                })
            },
            keepProps: "extKey hcEvents names series userMax userMin".split(" "),
            destroy: function(a) {
                var d = this,
                b = d.stacks,
                c = d.plotLinesAndBands,
                e;
                k(this, "destroy", {
                    keepEvents: a
                });
                a || h(d);
                D(b,
                function(d, a) {
                    A(d);
                    b[a] = null
                }); [d.ticks, d.minorTicks, d.alternateBands].forEach(function(d) {
                    A(d)
                });
                if (c) for (a = c.length; a--;) c[a].destroy();
                "stackTotalGroup axisLine axisTitle axisGroup gridGroup labelGroup cross scrollbar".split(" ").forEach(function(a) {
                    d[a] && (d[a] = d[a].destroy())
                });
                for (e in d.plotLinesAndBandsGroups) d.plotLinesAndBandsGroups[e] = d.plotLinesAndBandsGroups[e].destroy();
                D(d,
                function(a, b) { - 1 === d.keepProps.indexOf(b) && delete d[b]
                })
            },
            drawCrosshair: function(a, d) {
                var b = this.crosshair,
                c = w(b.snap, !0),
                h,
                e = this.cross,
                l = this.chart;
                k(this, "drawCrosshair", {
                    e: a,
                    point: d
                });
                a || (a = this.cross && this.cross.e);
                if (this.crosshair && !1 !== (x(d) || !c)) {
                    c ? x(d) && (h = w("colorAxis" !== this.coll ? d.crosshairPos: null, this.isXAxis ? d.plotX: this.len - d.plotY)) : h = a && (this.horiz ? a.chartX - this.pos: this.len - a.chartY + this.pos);
                    if (x(h)) {
                        var g = {
                            value: d && (this.isXAxis ? d.x: w(d.stackY, d.y)),
                            translatedValue: h
                        };
                        l.polar && n(g, {
                            isCrosshair: !0,
                            chartX: a && a.chartX,
                            chartY: a && a.chartY,
                            point: d
                        });
                        g = this.getPlotLinePath(g) || null
                    }
                    if (!x(g)) {
                        this.hideCrosshair();
                        return
                    }
                    c = this.categories && !this.isRadial;
                    e || (this.cross = e = l.renderer.path().addClass("highcharts-crosshair highcharts-crosshair-" + (c ? "category ": "thin ") + b.className).attr({
                        zIndex: w(b.zIndex, 2)
                    }).add(), l.styledMode || (e.attr({
                        stroke: b.color || (c ? E("#ccd6eb").setOpacity(.25).get() : "#cccccc"),
                        "stroke-width": w(b.width, 1)
                    }).css({
                        "pointer-events": "none"
                    }), b.dashStyle && e.attr({
                        dashstyle: b.dashStyle
                    })));
                    e.show().attr({
                        d: g
                    });
                    c && !b.width && e.attr({
                        "stroke-width": this.transA
                    });
                    this.cross.e = a
                } else this.hideCrosshair();
                k(this, "afterDrawCrosshair", {
                    e: a,
                    point: d
                })
            },
            hideCrosshair: function() {
                this.cross && this.cross.hide();
                k(this, "afterHideCrosshair")
            }
        });
        return f.Axis = m
    });
    P(y, "parts/DateTimeAxis.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.getMagnitude,
        r = m.normalizeTickInterval,
        E = m.timeUnits;
        f = f.Axis;
        f.prototype.getTimeTicks = function() {
            return this.chart.time.getTimeTicks.apply(this.chart.time, arguments)
        };
        f.prototype.normalizeTimeTickInterval = function(f, m) {
            var C = m || [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1, 2]], ["week", [1, 2]], ["month", [1, 2, 3, 4, 6]], ["year", null]];
            m = C[C.length - 1];
            var B = E[m[0]],
            F = m[1],
            z;
            for (z = 0; z < C.length && !(m = C[z], B = E[m[0]], F = m[1], C[z + 1] && f <= (B * F[F.length - 1] + E[C[z + 1][0]]) / 2); z++);
            B === E.year && f < 5 * B && (F = [1, 2, 5]);
            f = r(f / B, F, "year" === m[0] ? Math.max(J(f / B), 1) : 1);
            return {
                unitRange: B,
                count: f,
                unitName: m[0]
            }
        }
    });
    P(y, "parts/LogarithmicAxis.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.getMagnitude,
        r = m.normalizeTickInterval,
        E = m.pick;
        f = f.Axis;
        f.prototype.getLogTickPositions = function(f, m, C, B) {
            var F = this.options,
            z = this.len,
            x = [];
            B || (this._minorAutoInterval = null);
            if (.5 <= f) f = Math.round(f),
            x = this.getLinearTickPositions(f, m, C);
            else if (.08 <= f) {
                z = Math.floor(m);
                var A, t;
                for (F = .3 < f ? [1, 2, 4] : .15 < f ? [1, 2, 4, 6, 8] : [1, 2, 3, 4, 5, 6, 7, 8, 9]; z < C + 1 && !t; z++) {
                    var n = F.length;
                    for (A = 0; A < n && !t; A++) {
                        var k = this.log2lin(this.lin2log(z) * F[A]);
                        k > m && (!B || q <= C) && "undefined" !== typeof q && x.push(q);
                        q > C && (t = !0);
                        var q = k
                    }
                }
            } else m = this.lin2log(m),
            C = this.lin2log(C),
            f = B ? this.getMinorTickInterval() : F.tickInterval,
            f = E("auto" === f ? null: f, this._minorAutoInterval, F.tickPixelInterval / (B ? 5 : 1) * (C - m) / ((B ? z / this.tickPositions.length: z) || 1)),
            f = r(f, null, J(f)),
            x = this.getLinearTickPositions(f, m, C).map(this.log2lin),
            B || (this._minorAutoInterval = f / 5);
            B || (this.tickInterval = f);
            return x
        };
        f.prototype.log2lin = function(f) {
            return Math.log(f) / Math.LN10
        };
        f.prototype.lin2log = function(f) {
            return Math.pow(10, f)
        }
    });
    P(y, "parts/PlotLineOrBand.js", [y["parts/Globals.js"], y["parts/Axis.js"], y["parts/Utilities.js"]],
    function(f, m, J) {
        var r = J.arrayMax,
        E = J.arrayMin,
        N = J.defined,
        F = J.destroyObjectProperties,
        C = J.erase,
        B = J.extend,
        L = J.merge,
        z = J.objectEach,
        x = J.pick,
        A = function() {
            function t(n, k) {
                this.axis = n;
                k && (this.options = k, this.id = k.id)
            }
            t.prototype.render = function() {
                f.fireEvent(this, "render");
                var n = this,
                k = n.axis,
                q = k.horiz,
                e = n.options,
                c = e.label,
                g = n.label,
                p = e.to,
                b = e.from,
                a = e.value,
                v = N(b) && N(p),
                D = N(a),
                w = n.svgElem,
                l = !w,
                h = [],
                u = e.color,
                H = x(e.zIndex, 0),
                M = e.events;
                h = {
                    "class": "highcharts-plot-" + (v ? "band ": "line ") + (e.className || "")
                };
                var t = {},
                G = k.chart.renderer,
                d = v ? "bands": "lines";
                k.isLog && (b = k.log2lin(b), p = k.log2lin(p), a = k.log2lin(a));
                k.chart.styledMode || (D ? (h.stroke = u || "#999999", h["stroke-width"] = x(e.width, 1), e.dashStyle && (h.dashstyle = e.dashStyle)) : v && (h.fill = u || "#e6ebf5", e.borderWidth && (h.stroke = e.borderColor, h["stroke-width"] = e.borderWidth)));
                t.zIndex = H;
                d += "-" + H; (u = k.plotLinesAndBandsGroups[d]) || (k.plotLinesAndBandsGroups[d] = u = G.g("plot-" + d).attr(t).add());
                l && (n.svgElem = w = G.path().attr(h).add(u));
                if (D) h = k.getPlotLinePath({
                    value: a,
                    lineWidth: w.strokeWidth(),
                    acrossPanes: e.acrossPanes
                });
                else if (v) h = k.getPlotBandPath(b, p, e);
                else return; (l || !w.d) && h && h.length ? (w.attr({
                    d: h
                }), M && z(M,
                function(d, a) {
                    w.on(a,
                    function(d) {
                        M[a].apply(n, [d])
                    })
                })) : w && (h ? (w.show(!0), w.animate({
                    d: h
                })) : w.d && (w.hide(), g && (n.label = g = g.destroy())));
                c && (N(c.text) || N(c.formatter)) && h && h.length && 0 < k.width && 0 < k.height && !h.isFlat ? (c = L({
                    align: q && v && "center",
                    x: q ? !v && 4 : 10,
                    verticalAlign: !q && v && "middle",
                    y: q ? v ? 16 : 10 : v ? 6 : -4,
                    rotation: q && !v && 90
                },
                c), this.renderLabel(c, h, v, H)) : g && g.hide();
                return n
            };
            t.prototype.renderLabel = function(n, k, q, e) {
                var c = this.label,
                g = this.axis.chart.renderer;
                c || (c = {
                    align: n.textAlign || n.align,
                    rotation: n.rotation,
                    "class": "highcharts-plot-" + (q ? "band": "line") + "-label " + (n.className || "")
                },
                c.zIndex = e, e = this.getLabelText(n), this.label = c = g.text(e, 0, 0, n.useHTML).attr(c).add(), this.axis.chart.styledMode || c.css(n.style));
                g = k.xBounds || [k[1], k[4], q ? k[6] : k[1]];
                k = k.yBounds || [k[2], k[5], q ? k[7] : k[2]];
                q = E(g);
                e = E(k);
                c.align(n, !1, {
                    x: q,
                    y: e,
                    width: r(g) - q,
                    height: r(k) - e
                });
                c.show(!0)
            };
            t.prototype.getLabelText = function(n) {
                return N(n.formatter) ? n.formatter.call(this) : n.text
            };
            t.prototype.destroy = function() {
                C(this.axis.plotLinesAndBands, this);
                delete this.axis;
                F(this)
            };
            return t
        } ();
        B(m.prototype, {
            getPlotBandPath: function(f, n) {
                var k = this.getPlotLinePath({
                    value: n,
                    force: !0,
                    acrossPanes: this.options.acrossPanes
                }),
                q = this.getPlotLinePath({
                    value: f,
                    force: !0,
                    acrossPanes: this.options.acrossPanes
                }),
                e = [],
                c = this.horiz,
                g = 1;
                f = f < this.min && n < this.min || f > this.max && n > this.max;
                if (q && k) {
                    if (f) {
                        var p = q.toString() === k.toString();
                        g = 0
                    }
                    for (f = 0; f < q.length; f += 6) c && k[f + 1] === q[f + 1] ? (k[f + 1] += g, k[f + 4] += g) : c || k[f + 2] !== q[f + 2] || (k[f + 2] += g, k[f + 5] += g),
                    e.push("M", q[f + 1], q[f + 2], "L", q[f + 4], q[f + 5], k[f + 4], k[f + 5], k[f + 1], k[f + 2], "z"),
                    e.isFlat = p
                }
                return e
            },
            addPlotBand: function(f) {
                return this.addPlotBandOrLine(f, "plotBands")
            },
            addPlotLine: function(f) {
                return this.addPlotBandOrLine(f, "plotLines")
            },
            addPlotBandOrLine: function(f, n) {
                var k = (new A(this, f)).render(),
                q = this.userOptions;
                if (k) {
                    if (n) {
                        var e = q[n] || [];
                        e.push(f);
                        q[n] = e
                    }
                    this.plotLinesAndBands.push(k)
                }
                return k
            },
            removePlotBandOrLine: function(f) {
                for (var n = this.plotLinesAndBands,
                k = this.options,
                q = this.userOptions,
                e = n.length; e--;) n[e].id === f && n[e].destroy(); [k.plotLines || [], q.plotLines || [], k.plotBands || [], q.plotBands || []].forEach(function(c) {
                    for (e = c.length; e--;) c[e].id === f && C(c, c[e])
                })
            },
            removePlotBand: function(f) {
                this.removePlotBandOrLine(f)
            },
            removePlotLine: function(f) {
                this.removePlotBandOrLine(f)
            }
        });
        f.PlotLineOrBand = A;
        return f.PlotLineOrBand
    });
    P(y, "parts/Tooltip.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.clamp,
        r = m.css,
        E = m.defined,
        N = m.discardElement,
        F = m.extend,
        C = m.format,
        B = m.isNumber,
        L = m.isString,
        z = m.merge,
        x = m.pick,
        A = m.splat,
        t = m.syncTimeout,
        n = m.timeUnits;
        "";
        var k = f.doc,
        q = function() {
            function e(c, e) {
                this.crosshairs = [];
                this.distance = 0;
                this.isHidden = !0;
                this.isSticky = !1;
                this.now = {};
                this.options = {};
                this.outside = !1;
                this.chart = c;
                this.init(c, e)
            }
            e.prototype.applyFilter = function() {
                var c = this.chart;
                c.renderer.definition({
                    tagName: "filter",
                    id: "drop-shadow-" + c.index,
                    opacity: .5,
                    children: [{
                        tagName: "feGaussianBlur",
                        "in": "SourceAlpha",
                        stdDeviation: 1
                    },
                    {
                        tagName: "feOffset",
                        dx: 1,
                        dy: 1
                    },
                    {
                        tagName: "feComponentTransfer",
                        children: [{
                            tagName: "feFuncA",
                            type: "linear",
                            slope: .3
                        }]
                    },
                    {
                        tagName: "feMerge",
                        children: [{
                            tagName: "feMergeNode"
                        },
                        {
                            tagName: "feMergeNode",
                            "in": "SourceGraphic"
                        }]
                    }]
                });
                c.renderer.definition({
                    tagName: "style",
                    textContent: ".highcharts-tooltip-" + c.index + "{filter:url(#drop-shadow-" + c.index + ")}"
                })
            };
            e.prototype.bodyFormatter = function(c) {
                return c.map(function(c) {
                    var e = c.series.tooltipOptions;
                    return (e[(c.point.formatPrefix || "point") + "Formatter"] || c.point.tooltipFormatter).call(c.point, e[(c.point.formatPrefix || "point") + "Format"] || "")
                })
            };
            e.prototype.cleanSplit = function(c) {
                this.chart.series.forEach(function(e) {
                    var g = e && e.tt;
                    g && (!g.isActive || c ? e.tt = g.destroy() : g.isActive = !1)
                })
            };
            e.prototype.defaultFormatter = function(c) {
                var e = this.points || A(this);
                var p = [c.tooltipFooterHeaderFormatter(e[0])];
                p = p.concat(c.bodyFormatter(e));
                p.push(c.tooltipFooterHeaderFormatter(e[0], !0));
                return p
            };
            e.prototype.destroy = function() {
                this.label && (this.label = this.label.destroy());
                this.split && this.tt && (this.cleanSplit(this.chart, !0), this.tt = this.tt.destroy());
                this.renderer && (this.renderer = this.renderer.destroy(), N(this.container));
                m.clearTimeout(this.hideTimer);
                m.clearTimeout(this.tooltipTimeout)
            };
            e.prototype.getAnchor = function(c, e) {
                var g = this.chart,
                b = g.pointer,
                a = g.inverted,
                v = g.plotTop,
                k = g.plotLeft,
                w = 0,
                l = 0,
                h, u;
                c = A(c);
                this.followPointer && e ? ("undefined" === typeof e.chartX && (e = b.normalize(e)), c = [e.chartX - k, e.chartY - v]) : c[0].tooltipPos ? c = c[0].tooltipPos: (c.forEach(function(b) {
                    h = b.series.yAxis;
                    u = b.series.xAxis;
                    w += b.plotX + (!a && u ? u.left - k: 0);
                    l += (b.plotLow ? (b.plotLow + b.plotHigh) / 2 : b.plotY) + (!a && h ? h.top - v: 0)
                }), w /= c.length, l /= c.length, c = [a ? g.plotWidth - l: w, this.shared && !a && 1 < c.length && e ? e.chartY - v: a ? g.plotHeight - w: l]);
                return c.map(Math.round)
            };
            e.prototype.getDateFormat = function(c, e, p, b) {
                var a = this.chart.time,
                g = a.dateFormat("%m-%d %H:%M:%S.%L", e),
                k = {
                    millisecond: 15,
                    second: 12,
                    minute: 9,
                    hour: 6,
                    day: 3
                },
                w = "millisecond";
                for (l in n) {
                    if (c === n.week && +a.dateFormat("%w", e) === p && "00:00:00.000" === g.substr(6)) {
                        var l = "week";
                        break
                    }
                    if (n[l] > c) {
                        l = w;
                        break
                    }
                    if (k[l] && g.substr(k[l]) !== "01-01 00:00:00.000".substr(k[l])) break;
                    "week" !== l && (w = l)
                }
                if (l) var h = a.resolveDTLFormat(b[l]).main;
                return h
            };
            e.prototype.getLabel = function() {
                var c, e = this,
                p = this.chart.renderer,
                b = this.chart.styledMode,
                a = this.options,
                v = "tooltip" + (E(a.className) ? " " + a.className: ""),
                k = (null === (c = a.style) || void 0 === c ? void 0 : c.pointerEvents) || (!this.followPointer && a.stickOnContact ? "auto": "none"),
                w;
                c = function() {
                    e.inContact = !0
                };
                var l = function() {
                    var a = e.chart.hoverSeries;
                    e.inContact = !1;
                    if (a && a.onMouseOut) a.onMouseOut()
                };
                if (!this.label) {
                    this.outside && (this.container = w = f.doc.createElement("div"), w.className = "highcharts-tooltip-container", r(w, {
                        position: "absolute",
                        top: "1px",
                        pointerEvents: k,
                        zIndex: 3
                    }), f.doc.body.appendChild(w), this.renderer = p = new f.Renderer(w, 0, 0, {},
                    void 0, void 0, p.styledMode));
                    this.split ? this.label = p.g(v) : (this.label = p.label("", 0, 0, a.shape || "callout", null, null, a.useHTML, null, v).attr({
                        padding: a.padding,
                        r: a.borderRadius
                    }), b || this.label.attr({
                        fill: a.backgroundColor,
                        "stroke-width": a.borderWidth
                    }).css(a.style).css({
                        pointerEvents: k
                    }).shadow(a.shadow));
                    b && (this.applyFilter(), this.label.addClass("highcharts-tooltip-" + this.chart.index));
                    if (e.outside && !e.split) {
                        var h = {
                            x: this.label.xSetter,
                            y: this.label.ySetter
                        };
                        this.label.xSetter = function(a, b) {
                            h[b].call(this.label, e.distance);
                            w.style.left = a + "px"
                        };
                        this.label.ySetter = function(a, b) {
                            h[b].call(this.label, e.distance);
                            w.style.top = a + "px"
                        }
                    }
                    this.label.on("mouseenter", c).on("mouseleave", l).attr({
                        zIndex: 8
                    }).add()
                }
                return this.label
            };
            e.prototype.getPosition = function(c, e, p) {
                var b = this.chart,
                a = this.distance,
                g = {},
                D = b.inverted && p.h || 0,
                w, l = this.outside,
                h = l ? k.documentElement.clientWidth - 2 * a: b.chartWidth,
                u = l ? Math.max(k.body.scrollHeight, k.documentElement.scrollHeight, k.body.offsetHeight, k.documentElement.offsetHeight, k.documentElement.clientHeight) : b.chartHeight,
                f = b.pointer.getChartPosition(),
                n = b.containerScaling,
                q = function(d) {
                    return n ? d * n.scaleX: d
                },
                G = function(d) {
                    return n ? d * n.scaleY: d
                },
                d = function(d) {
                    var g = "x" === d;
                    return [d, g ? h: u, g ? c: e].concat(l ? [g ? q(c) : G(e), g ? f.left - a + q(p.plotX + b.plotLeft) : f.top - a + G(p.plotY + b.plotTop), 0, g ? h: u] : [g ? c: e, g ? p.plotX + b.plotLeft: p.plotY + b.plotTop, g ? b.plotLeft: b.plotTop, g ? b.plotLeft + b.plotWidth: b.plotTop + b.plotHeight])
                },
                I = d("y"),
                O = d("x"),
                t = !this.followPointer && x(p.ttBelow, !b.inverted === !!p.negative),
                z = function(d, b, c, h, e, l, p) {
                    var u = "y" === d ? G(a) : q(a),
                    v = (c - h) / 2,
                    k = h < e - a,
                    w = e + a + h < b,
                    I = e - u - c + v;
                    e = e + u - v;
                    if (t && w) g[d] = e;
                    else if (!t && k) g[d] = I;
                    else if (k) g[d] = Math.min(p - h, 0 > I - D ? I: I - D);
                    else if (w) g[d] = Math.max(l, e + D + c > b ? e: e + D);
                    else return ! 1
                },
                m = function(d, b, c, h, e) {
                    var l;
                    e < a || e > b - a ? l = !1 : g[d] = e < c / 2 ? 1 : e > b - h / 2 ? b - h - 2 : e - c / 2;
                    return l
                },
                A = function(d) {
                    var a = I;
                    I = O;
                    O = a;
                    w = d
                },
                K = function() { ! 1 !== z.apply(0, I) ? !1 !== m.apply(0, O) || w || (A(!0), K()) : w ? g.x = g.y = 0 : (A(!0), K())
                }; (b.inverted || 1 < this.len) && A();
                K();
                return g
            };
            e.prototype.getXDateFormat = function(c, e, p) {
                e = e.dateTimeLabelFormats;
                var b = p && p.closestPointRange;
                return (b ? this.getDateFormat(b, c.x, p.options.startOfWeek, e) : e.day) || e.year
            };
            e.prototype.hide = function(c) {
                var e = this;
                m.clearTimeout(this.hideTimer);
                c = x(c, this.options.hideDelay, 500);
                this.isHidden || (this.hideTimer = t(function() {
                    e.getLabel()[c ? "fadeOut": "hide"]();
                    e.isHidden = !0
                },
                c))
            };
            e.prototype.init = function(c, e) {
                this.chart = c;
                this.options = e;
                this.crosshairs = [];
                this.now = {
                    x: 0,
                    y: 0
                };
                this.isHidden = !0;
                this.split = e.split && !c.inverted && !c.polar;
                this.shared = e.shared || this.split;
                this.outside = x(e.outside, !(!c.scrollablePixelsX && !c.scrollablePixelsY))
            };
            e.prototype.isStickyOnContact = function() {
                return ! (this.followPointer || !this.options.stickOnContact || !this.inContact)
            };
            e.prototype.move = function(c, e, p, b) {
                var a = this,
                g = a.now,
                k = !1 !== a.options.animation && !a.isHidden && (1 < Math.abs(c - g.x) || 1 < Math.abs(e - g.y)),
                w = a.followPointer || 1 < a.len;
                F(g, {
                    x: k ? (2 * g.x + c) / 3 : c,
                    y: k ? (g.y + e) / 2 : e,
                    anchorX: w ? void 0 : k ? (2 * g.anchorX + p) / 3 : p,
                    anchorY: w ? void 0 : k ? (g.anchorY + b) / 2 : b
                });
                a.getLabel().attr(g);
                a.drawTracker();
                k && (m.clearTimeout(this.tooltipTimeout), this.tooltipTimeout = setTimeout(function() {
                    a && a.move(c, e, p, b)
                },
                32))
            };
            e.prototype.refresh = function(c, e) {
                var g = this.chart,
                b = this.options,
                a = c,
                v = {},
                k = [],
                w = b.formatter || this.defaultFormatter;
                v = this.shared;
                var l = g.styledMode;
                if (b.enabled) {
                    m.clearTimeout(this.hideTimer);
                    this.followPointer = A(a)[0].series.tooltipOptions.followPointer;
                    var h = this.getAnchor(a, e);
                    e = h[0];
                    var u = h[1]; ! v || a.series && a.series.noSharedTooltip ? v = a.getLabelConfig() : (g.pointer.applyInactiveState(a), a.forEach(function(a) {
                        a.setState("hover");
                        k.push(a.getLabelConfig())
                    }), v = {
                        x: a[0].category,
                        y: a[0].y
                    },
                    v.points = k, a = a[0]);
                    this.len = k.length;
                    g = w.call(v, this);
                    w = a.series;
                    this.distance = x(w.tooltipOptions.distance, 16); ! 1 === g ? this.hide() : (this.split ? this.renderSplit(g, A(c)) : (c = this.getLabel(), b.style.width && !l || c.css({
                        width: this.chart.spacingBox.width
                    }), c.attr({
                        text: g && g.join ? g.join("") : g
                    }), c.removeClass(/highcharts-color-[\d]+/g).addClass("highcharts-color-" + x(a.colorIndex, w.colorIndex)), l || c.attr({
                        stroke: b.borderColor || a.color || w.color || "#666666"
                    }), this.updatePosition({
                        plotX: e,
                        plotY: u,
                        negative: a.negative,
                        ttBelow: a.ttBelow,
                        h: h[2] || 0
                    })), this.isHidden && this.label && this.label.attr({
                        opacity: 1
                    }).show(), this.isHidden = !1);
                    f.fireEvent(this, "refresh")
                }
            };
            e.prototype.renderSplit = function(c, e) {
                function g(d, a, b, c, e) {
                    void 0 === e && (e = !0);
                    b ? (a = r ? 0 : C, d = J(d - c / 2, m.left, m.right - c)) : (a -= K, d = e ? d - c - I: d + I, d = J(d, e ? d: m.left, m.right));
                    return {
                        x: d,
                        y: a
                    }
                }
                var b = this,
                a = b.chart,
                v = b.chart,
                k = v.plotHeight,
                w = v.plotLeft,
                l = v.plotTop,
                h = v.pointer,
                u = v.renderer,
                n = v.scrollablePixelsY,
                q = void 0 === n ? 0 : n;
                n = v.scrollingContainer;
                n = void 0 === n ? {
                    scrollLeft: 0,
                    scrollTop: 0
                }: n;
                var t = n.scrollLeft,
                G = n.scrollTop,
                d = v.styledMode,
                I = b.distance,
                O = b.options,
                z = b.options.positioner,
                m = {
                    left: t,
                    right: t + v.chartWidth,
                    top: G,
                    bottom: G + v.chartHeight
                },
                A = b.getLabel(),
                r = !(!a.xAxis[0] || !a.xAxis[0].opposite),
                K = l + G,
                B = 0,
                C = k - q;
                L(c) && (c = [!1, c]);
                c = c.slice(0, e.length + 1).reduce(function(a, c, h) {
                    if (!1 !== c && "" !== c) {
                        h = e[h - 1] || {
                            isHeader: !0,
                            plotX: e[0].plotX,
                            plotY: k,
                            series: {}
                        };
                        var p = h.isHeader,
                        v = p ? b: h.series,
                        D = v.tt,
                        n = h.isHeader;
                        var f = h.series;
                        var H = "highcharts-color-" + x(h.colorIndex, f.colorIndex, "none");
                        D || (D = {
                            padding: O.padding,
                            r: O.borderRadius
                        },
                        d || (D.fill = O.backgroundColor, D["stroke-width"] = O.borderWidth), D = u.label("", 0, 0, O[n ? "headerShape": "shape"] || "callout", void 0, void 0, O.useHTML).addClass((n ? "highcharts-tooltip-header ": "") + "highcharts-tooltip-box " + H).attr(D).add(A));
                        D.isActive = !0;
                        D.attr({
                            text: c
                        });
                        d || D.css(O.style).shadow(O.shadow).attr({
                            stroke: O.borderColor || h.color || f.color || "#333333"
                        });
                        c = v.tt = D;
                        n = c.getBBox();
                        v = n.width + c.strokeWidth();
                        p && (B = n.height, C += B, r && (K -= B));
                        f = h.plotX;
                        f = void 0 === f ? 0 : f;
                        H = h.plotY;
                        H = void 0 === H ? 0 : H;
                        var M = h.series;
                        if (h.isHeader) {
                            f = w + f;
                            var t = l + k / 2
                        } else D = M.xAxis,
                        M = M.yAxis,
                        f = D.pos + J(f, -I, D.len + I),
                        M.pos + H >= G + l && M.pos + H <= G + l + k - q && (t = M.pos + H);
                        f = J(f, m.left - I, m.right + I);
                        "number" === typeof t ? (n = n.height + 1, H = z ? z.call(b, v, n, h) : g(f, t, p, v), a.push({
                            align: z ? 0 : void 0,
                            anchorX: f,
                            anchorY: t,
                            boxWidth: v,
                            point: h,
                            rank: x(H.rank, p ? 1 : 0),
                            size: n,
                            target: H.y,
                            tt: c,
                            x: H.x
                        })) : c.isActive = !1
                    }
                    return a
                },
                []); ! z && c.some(function(d) {
                    return d.x < m.left
                }) && (c = c.map(function(d) {
                    var a = g(d.anchorX, d.anchorY, d.point.isHeader, d.boxWidth, !1);
                    return F(d, {
                        target: a.y,
                        x: a.x
                    })
                }));
                b.cleanSplit();
                f.distribute(c, C);
                c.forEach(function(d) {
                    var a = d.pos;
                    d.tt.attr({
                        visibility: "undefined" === typeof a ? "hidden": "inherit",
                        x: d.x,
                        y: a + K,
                        anchorX: d.anchorX,
                        anchorY: d.anchorY
                    })
                });
                c = b.container;
                a = b.renderer;
                b.outside && c && a && (v = A.getBBox(), a.setSize(v.width + v.x, v.height + v.y, !1), h = h.getChartPosition(), c.style.left = h.left + "px", c.style.top = h.top + "px")
            };
            e.prototype.drawTracker = function() {
                if (this.followPointer || !this.options.stickOnContact) this.tracker && this.tracker.destroy();
                else {
                    var c = this.chart,
                    e = this.label,
                    p = c.hoverPoint;
                    if (e && p) {
                        var b = {
                            x: 0,
                            y: 0,
                            width: 0,
                            height: 0
                        };
                        p = this.getAnchor(p);
                        var a = e.getBBox();
                        p[0] += c.plotLeft - e.translateX;
                        p[1] += c.plotTop - e.translateY;
                        b.x = Math.min(0, p[0]);
                        b.y = Math.min(0, p[1]);
                        b.width = 0 > p[0] ? Math.max(Math.abs(p[0]), a.width - p[0]) : Math.max(Math.abs(p[0]), a.width);
                        b.height = 0 > p[1] ? Math.max(Math.abs(p[1]), a.height - Math.abs(p[1])) : Math.max(Math.abs(p[1]), a.height);
                        this.tracker ? this.tracker.attr(b) : (this.tracker = e.renderer.rect(b).addClass("highcharts-tracker").add(e), c.styledMode || this.tracker.attr({
                            fill: "rgba(0,0,0,0)"
                        }))
                    }
                }
            };
            e.prototype.styledModeFormat = function(c) {
                return c.replace('style="font-size: 10px"', 'class="highcharts-header"').replace(/style="color:{(point|series)\.color}"/g, 'class="highcharts-color-{$1.colorIndex}"')
            };
            e.prototype.tooltipFooterHeaderFormatter = function(c, e) {
                var g = e ? "footer": "header",
                b = c.series,
                a = b.tooltipOptions,
                v = a.xDateFormat,
                k = b.xAxis,
                w = k && "datetime" === k.options.type && B(c.key),
                l = a[g + "Format"];
                e = {
                    isFooter: e,
                    labelConfig: c
                };
                f.fireEvent(this, "headerFormatter", e,
                function(e) {
                    w && !v && (v = this.getXDateFormat(c, a, k));
                    w && v && (c.point && c.point.tooltipDateKeys || ["key"]).forEach(function(a) {
                        l = l.replace("{point." + a + "}", "{point." + a + ":" + v + "}")
                    });
                    b.chart.styledMode && (l = this.styledModeFormat(l));
                    e.text = C(l, {
                        point: c,
                        series: b
                    },
                    this.chart)
                });
                return e.text
            };
            e.prototype.update = function(c) {
                this.destroy();
                z(!0, this.chart.options.tooltip.userOptions, c);
                this.init(this.chart, z(!0, this.options, c))
            };
            e.prototype.updatePosition = function(c) {
                var e = this.chart,
                p = e.pointer,
                b = this.getLabel(),
                a = c.plotX + e.plotLeft,
                v = c.plotY + e.plotTop;
                p = p.getChartPosition();
                c = (this.options.positioner || this.getPosition).call(this, b.width, b.height, c);
                if (this.outside) {
                    var k = (this.options.borderWidth || 0) + 2 * this.distance;
                    this.renderer.setSize(b.width + k, b.height + k, !1);
                    if (e = e.containerScaling) r(this.container, {
                        transform: "scale(" + e.scaleX + ", " + e.scaleY + ")"
                    }),
                    a *= e.scaleX,
                    v *= e.scaleY;
                    a += p.left - c.x;
                    v += p.top - c.y
                }
                this.move(Math.round(c.x), Math.round(c.y || 0), a, v)
            };
            return e
        } ();
        f.Tooltip = q;
        return f.Tooltip
    });
    P(y, "parts/Pointer.js", [y["parts/Globals.js"], y["parts/Utilities.js"], y["parts/Tooltip.js"], y["parts/Color.js"]],
    function(f, m, J, r) {
        var E = m.addEvent,
        N = m.attr,
        F = m.css,
        C = m.defined,
        B = m.extend,
        L = m.find,
        z = m.fireEvent,
        x = m.isNumber,
        A = m.isObject,
        t = m.objectEach,
        n = m.offset,
        k = m.pick,
        q = m.splat,
        e = r.parse,
        c = f.charts,
        g = f.noop;
        m = function() {
            function p(b, a) {
                this.lastValidTouch = {};
                this.pinchDown = [];
                this.runChartClick = !1;
                this.chart = b;
                this.hasDragged = !1;
                this.options = a;
                this.unbindContainerMouseLeave = function() {};
                this.init(b, a)
            }
            p.prototype.applyInactiveState = function(b) {
                var a = [],
                c; (b || []).forEach(function(b) {
                    c = b.series;
                    a.push(c);
                    c.linkedParent && a.push(c.linkedParent);
                    c.linkedSeries && (a = a.concat(c.linkedSeries));
                    c.navigatorSeries && a.push(c.navigatorSeries)
                });
                this.chart.series.forEach(function(b) { - 1 === a.indexOf(b) ? b.setState("inactive", !0) : b.options.inactiveOtherPoints && b.setAllPointsToState("inactive")
                })
            };
            p.prototype.destroy = function() {
                var b = this;
                "undefined" !== typeof b.unDocMouseMove && b.unDocMouseMove();
                this.unbindContainerMouseLeave();
                f.chartCount || (f.unbindDocumentMouseUp && (f.unbindDocumentMouseUp = f.unbindDocumentMouseUp()), f.unbindDocumentTouchEnd && (f.unbindDocumentTouchEnd = f.unbindDocumentTouchEnd()));
                clearInterval(b.tooltipTimeout);
                t(b,
                function(a, c) {
                    b[c] = null
                })
            };
            p.prototype.drag = function(b) {
                var a = this.chart,
                c = a.options.chart,
                g = b.chartX,
                p = b.chartY,
                l = this.zoomHor,
                h = this.zoomVert,
                u = a.plotLeft,
                k = a.plotTop,
                n = a.plotWidth,
                f = a.plotHeight,
                q = this.selectionMarker,
                d = this.mouseDownX || 0,
                I = this.mouseDownY || 0,
                O = A(c.panning) ? c.panning && c.panning.enabled: c.panning,
                t = c.panKey && b[c.panKey + "Key"];
                if (!q || !q.touch) if (g < u ? g = u: g > u + n && (g = u + n), p < k ? p = k: p > k + f && (p = k + f), this.hasDragged = Math.sqrt(Math.pow(d - g, 2) + Math.pow(I - p, 2)), 10 < this.hasDragged) {
                    var x = a.isInsidePlot(d - u, I - k);
                    a.hasCartesianSeries && (this.zoomX || this.zoomY) && x && !t && !q && (this.selectionMarker = q = a.renderer.rect(u, k, l ? 1 : n, h ? 1 : f, 0).attr({
                        "class": "highcharts-selection-marker",
                        zIndex: 7
                    }).add(), a.styledMode || q.attr({
                        fill: c.selectionMarkerFill || e("#335cad").setOpacity(.25).get()
                    }));
                    q && l && (g -= d, q.attr({
                        width: Math.abs(g),
                        x: (0 < g ? 0 : g) + d
                    }));
                    q && h && (g = p - I, q.attr({
                        height: Math.abs(g),
                        y: (0 < g ? 0 : g) + I
                    }));
                    x && !q && O && a.pan(b, c.panning)
                }
            };
            p.prototype.dragStart = function(b) {
                var a = this.chart;
                a.mouseIsDown = b.type;
                a.cancelClick = !1;
                a.mouseDownX = this.mouseDownX = b.chartX;
                a.mouseDownY = this.mouseDownY = b.chartY
            };
            p.prototype.drop = function(b) {
                var a = this,
                c = this.chart,
                e = this.hasPinched;
                if (this.selectionMarker) {
                    var g = {
                        originalEvent: b,
                        xAxis: [],
                        yAxis: []
                    },
                    l = this.selectionMarker,
                    h = l.attr ? l.attr("x") : l.x,
                    p = l.attr ? l.attr("y") : l.y,
                    k = l.attr ? l.attr("width") : l.width,
                    n = l.attr ? l.attr("height") : l.height,
                    f;
                    if (this.hasDragged || e) c.axes.forEach(function(c) {
                        if (c.zoomEnabled && C(c.min) && (e || a[{
                            xAxis: "zoomX",
                            yAxis: "zoomY"
                        } [c.coll]])) {
                            var d = c.horiz,
                            l = "touchend" === b.type ? c.minPixelPadding: 0,
                            u = c.toValue((d ? h: p) + l);
                            d = c.toValue((d ? h + k: p + n) - l);
                            g[c.coll].push({
                                axis: c,
                                min: Math.min(u, d),
                                max: Math.max(u, d)
                            });
                            f = !0
                        }
                    }),
                    f && z(c, "selection", g,
                    function(a) {
                        c.zoom(B(a, e ? {
                            animation: !1
                        }: null))
                    });
                    x(c.index) && (this.selectionMarker = this.selectionMarker.destroy());
                    e && this.scaleGroups()
                }
                c && x(c.index) && (F(c.container, {
                    cursor: c._cursor
                }), c.cancelClick = 10 < this.hasDragged, c.mouseIsDown = this.hasDragged = this.hasPinched = !1, this.pinchDown = [])
            };
            p.prototype.findNearestKDPoint = function(b, a, c) {
                var e = this.chart,
                g = e.hoverPoint;
                e = e.tooltip;
                if (g && e && e.isStickyOnContact()) return g;
                var l;
                b.forEach(function(b) {
                    var e = !(b.noSharedTooltip && a) && 0 > b.options.findNearestPointBy.indexOf("y");
                    b = b.searchPoint(c, e);
                    if ((e = A(b, !0)) && !(e = !A(l, !0))) {
                        e = l.distX - b.distX;
                        var h = l.dist - b.dist,
                        g = (b.series.group && b.series.group.zIndex) - (l.series.group && l.series.group.zIndex);
                        e = 0 < (0 !== e && a ? e: 0 !== h ? h: 0 !== g ? g: l.series.index > b.series.index ? -1 : 1)
                    }
                    e && (l = b)
                });
                return l
            };
            p.prototype.getChartCoordinatesFromPoint = function(b, a) {
                var c = b.series,
                e = c.xAxis;
                c = c.yAxis;
                var g = k(b.clientX, b.plotX),
                l = b.shapeArgs;
                if (e && c) return a ? {
                    chartX: e.len + e.pos - g,
                    chartY: c.len + c.pos - b.plotY
                }: {
                    chartX: g + e.pos,
                    chartY: b.plotY + c.pos
                };
                if (l && l.x && l.y) return {
                    chartX: l.x,
                    chartY: l.y
                }
            };
            p.prototype.getChartPosition = function() {
                return this.chartPosition || (this.chartPosition = n(this.chart.container))
            };
            p.prototype.getCoordinates = function(b) {
                var a = {
                    xAxis: [],
                    yAxis: []
                };
                this.chart.axes.forEach(function(c) {
                    a[c.isXAxis ? "xAxis": "yAxis"].push({
                        axis: c,
                        value: c.toValue(b[c.horiz ? "chartX": "chartY"])
                    })
                });
                return a
            };
            p.prototype.getHoverData = function(b, a, c, e, g, l) {
                var h, p = [];
                e = !(!e || !b);
                var v = a && !a.stickyTracking,
                w = {
                    chartX: l ? l.chartX: void 0,
                    chartY: l ? l.chartY: void 0,
                    shared: g
                };
                z(this, "beforeGetHoverData", w);
                v = v ? [a] : c.filter(function(a) {
                    return w.filter ? w.filter(a) : a.visible && !(!g && a.directTouch) && k(a.options.enableMouseTracking, !0) && a.stickyTracking
                });
                a = (h = e || !l ? b: this.findNearestKDPoint(v, g, l)) && h.series;
                h && (g && !a.noSharedTooltip ? (v = c.filter(function(a) {
                    return w.filter ? w.filter(a) : a.visible && !(!g && a.directTouch) && k(a.options.enableMouseTracking, !0) && !a.noSharedTooltip
                }), v.forEach(function(a) {
                    var b = L(a.points,
                    function(d) {
                        return d.x === h.x && !d.isNull
                    });
                    A(b) && (a.chart.isBoosting && (b = a.getPoint(b)), p.push(b))
                })) : p.push(h));
                w = {
                    hoverPoint: h
                };
                z(this, "afterGetHoverData", w);
                return {
                    hoverPoint: w.hoverPoint,
                    hoverSeries: a,
                    hoverPoints: p
                }
            };
            p.prototype.getPointFromEvent = function(b) {
                b = b.target;
                for (var a; b && !a;) a = b.point,
                b = b.parentNode;
                return a
            };
            p.prototype.onTrackerMouseOut = function(b) {
                var a = this.chart.hoverSeries;
                b = b.relatedTarget || b.toElement;
                this.isDirectTouch = !1;
                if (! (!a || !b || a.stickyTracking || this.inClass(b, "highcharts-tooltip") || this.inClass(b, "highcharts-series-" + a.index) && this.inClass(b, "highcharts-tracker"))) a.onMouseOut()
            };
            p.prototype.inClass = function(b, a) {
                for (var c; b;) {
                    if (c = N(b, "class")) {
                        if ( - 1 !== c.indexOf(a)) return ! 0;
                        if ( - 1 !== c.indexOf("highcharts-container")) return ! 1
                    }
                    b = b.parentNode
                }
            };
            p.prototype.init = function(b, a) {
                this.options = a;
                this.chart = b;
                this.runChartClick = a.chart.events && !!a.chart.events.click;
                this.pinchDown = [];
                this.lastValidTouch = {};
                J && (b.tooltip = new J(b, a.tooltip), this.followTouchMove = k(a.tooltip.followTouchMove, !0));
                this.setDOMEvents()
            };
            p.prototype.normalize = function(b, a) {
                var c = b.touches,
                e = c ? c.length ? c.item(0) : c.changedTouches[0] : b;
                a || (a = this.getChartPosition());
                c = e.pageX - a.left;
                a = e.pageY - a.top;
                if (e = this.chart.containerScaling) c /= e.scaleX,
                a /= e.scaleY;
                return B(b, {
                    chartX: Math.round(c),
                    chartY: Math.round(a)
                })
            };
            p.prototype.onContainerClick = function(b) {
                var a = this.chart,
                c = a.hoverPoint,
                e = a.plotLeft,
                g = a.plotTop;
                b = this.normalize(b);
                a.cancelClick || (c && this.inClass(b.target, "highcharts-tracker") ? (z(c.series, "click", B(b, {
                    point: c
                })), a.hoverPoint && c.firePointEvent("click", b)) : (B(b, this.getCoordinates(b)), a.isInsidePlot(b.chartX - e, b.chartY - g) && z(a, "click", b)))
            };
            p.prototype.onContainerMouseDown = function(b) {
                b = this.normalize(b);
                2 !== b.button && (this.zoomOption(b), b.preventDefault && b.preventDefault(), this.dragStart(b))
            };
            p.prototype.onContainerMouseLeave = function(b) {
                var a = c[f.hoverChartIndex];
                a && (b.relatedTarget || b.toElement) && (a.pointer.reset(), a.pointer.chartPosition = void 0)
            };
            p.prototype.onContainerMouseMove = function(b) {
                var a = this.chart;
                C(f.hoverChartIndex) && c[f.hoverChartIndex] && c[f.hoverChartIndex].mouseIsDown || (f.hoverChartIndex = a.index);
                b = this.normalize(b);
                b.preventDefault || (b.returnValue = !1);
                "mousedown" === a.mouseIsDown && this.drag(b);
                a.openMenu || !this.inClass(b.target, "highcharts-tracker") && !a.isInsidePlot(b.chartX - a.plotLeft, b.chartY - a.plotTop) || this.runPointActions(b)
            };
            p.prototype.onDocumentTouchEnd = function(b) {
                c[f.hoverChartIndex] && c[f.hoverChartIndex].pointer.drop(b)
            };
            p.prototype.onContainerTouchMove = function(b) {
                this.touch(b)
            };
            p.prototype.onContainerTouchStart = function(b) {
                this.zoomOption(b);
                this.touch(b, !0)
            };
            p.prototype.onDocumentMouseMove = function(b) {
                var a = this.chart,
                c = this.chartPosition,
                e = a.tooltip;
                b = this.normalize(b, c); ! c || e && e.isStickyOnContact() || a.isInsidePlot(b.chartX - a.plotLeft, b.chartY - a.plotTop) || this.inClass(b.target, "highcharts-tracker") || this.reset()
            };
            p.prototype.onDocumentMouseUp = function(b) {
                c[f.hoverChartIndex] && c[f.hoverChartIndex].pointer.drop(b)
            };
            p.prototype.pinch = function(b) {
                var a = this,
                c = a.chart,
                e = a.pinchDown,
                p = b.touches || [],
                l = p.length,
                h = a.lastValidTouch,
                u = a.hasZoom,
                n = a.selectionMarker,
                f = {},
                q = 1 === l && (a.inClass(b.target, "highcharts-tracker") && c.runTrackerClick || a.runChartClick),
                G = {};
                1 < l && (a.initiated = !0);
                u && a.initiated && !q && b.preventDefault(); [].map.call(p,
                function(d) {
                    return a.normalize(d)
                });
                "touchstart" === b.type ? ([].forEach.call(p,
                function(d, a) {
                    e[a] = {
                        chartX: d.chartX,
                        chartY: d.chartY
                    }
                }), h.x = [e[0].chartX, e[1] && e[1].chartX], h.y = [e[0].chartY, e[1] && e[1].chartY], c.axes.forEach(function(d) {
                    if (d.zoomEnabled) {
                        var a = c.bounds[d.horiz ? "h": "v"],
                        b = d.minPixelPadding,
                        e = d.toPixels(Math.min(k(d.options.min, d.dataMin), d.dataMin)),
                        h = d.toPixels(Math.max(k(d.options.max, d.dataMax), d.dataMax)),
                        l = Math.max(e, h);
                        a.min = Math.min(d.pos, Math.min(e, h) - b);
                        a.max = Math.max(d.pos + d.len, l + b)
                    }
                }), a.res = !0) : a.followTouchMove && 1 === l ? this.runPointActions(a.normalize(b)) : e.length && (n || (a.selectionMarker = n = B({
                    destroy: g,
                    touch: !0
                },
                c.plotBox)), a.pinchTranslate(e, p, f, n, G, h), a.hasPinched = u, a.scaleGroups(f, G), a.res && (a.res = !1, this.reset(!1, 0)))
            };
            p.prototype.pinchTranslate = function(b, a, c, e, g, l) {
                this.zoomHor && this.pinchTranslateDirection(!0, b, a, c, e, g, l);
                this.zoomVert && this.pinchTranslateDirection(!1, b, a, c, e, g, l)
            };
            p.prototype.pinchTranslateDirection = function(b, a, c, e, g, l, h, p) {
                var u = this.chart,
                k = b ? "x": "y",
                v = b ? "X": "Y",
                w = "chart" + v,
                d = b ? "width": "height",
                n = u["plot" + (b ? "Left": "Top")],
                f,
                q,
                D = p || 1,
                t = u.inverted,
                x = u.bounds[b ? "h": "v"],
                z = 1 === a.length,
                m = a[0][w],
                A = c[0][w],
                r = !z && a[1][w],
                S = !z && c[1][w];
                c = function() {
                    "number" === typeof S && 20 < Math.abs(m - r) && (D = p || Math.abs(A - S) / Math.abs(m - r));
                    q = (n - A) / D + m;
                    f = u["plot" + (b ? "Width": "Height")] / D
                };
                c();
                a = q;
                if (a < x.min) {
                    a = x.min;
                    var B = !0
                } else a + f > x.max && (a = x.max - f, B = !0);
                B ? (A -= .8 * (A - h[k][0]), "number" === typeof S && (S -= .8 * (S - h[k][1])), c()) : h[k] = [A, S];
                t || (l[k] = q - n, l[d] = f);
                l = t ? 1 / D: D;
                g[d] = f;
                g[k] = a;
                e[t ? b ? "scaleY": "scaleX": "scale" + v] = D;
                e["translate" + v] = l * n + (A - l * m)
            };
            p.prototype.reset = function(b, a) {
                var c = this.chart,
                e = c.hoverSeries,
                g = c.hoverPoint,
                l = c.hoverPoints,
                h = c.tooltip,
                p = h && h.shared ? l: g;
                b && p && q(p).forEach(function(a) {
                    a.series.isCartesian && "undefined" === typeof a.plotX && (b = !1)
                });
                if (b) h && p && q(p).length && (h.refresh(p), h.shared && l ? l.forEach(function(a) {
                    a.setState(a.state, !0);
                    a.series.isCartesian && (a.series.xAxis.crosshair && a.series.xAxis.drawCrosshair(null, a), a.series.yAxis.crosshair && a.series.yAxis.drawCrosshair(null, a))
                }) : g && (g.setState(g.state, !0), c.axes.forEach(function(a) {
                    a.crosshair && g.series[a.coll] === a && a.drawCrosshair(null, g)
                })));
                else {
                    if (g) g.onMouseOut();
                    l && l.forEach(function(a) {
                        a.setState()
                    });
                    if (e) e.onMouseOut();
                    h && h.hide(a);
                    this.unDocMouseMove && (this.unDocMouseMove = this.unDocMouseMove());
                    c.axes.forEach(function(a) {
                        a.hideCrosshair()
                    });
                    this.hoverX = c.hoverPoints = c.hoverPoint = null
                }
            };
            p.prototype.runPointActions = function(b, a) {
                var e = this.chart,
                g = e.tooltip && e.tooltip.options.enabled ? e.tooltip: void 0,
                p = g ? g.shared: !1,
                l = a || e.hoverPoint,
                h = l && l.series || e.hoverSeries;
                h = this.getHoverData(l, h, e.series, (!b || "touchmove" !== b.type) && ( !! a || h && h.directTouch && this.isDirectTouch), p, b);
                l = h.hoverPoint;
                var u = h.hoverPoints;
                a = (h = h.hoverSeries) && h.tooltipOptions.followPointer;
                p = p && h && !h.noSharedTooltip;
                if (l && (l !== e.hoverPoint || g && g.isHidden)) { (e.hoverPoints || []).forEach(function(a) { - 1 === u.indexOf(a) && a.setState()
                    });
                    if (e.hoverSeries !== h) h.onMouseOver();
                    this.applyInactiveState(u); (u || []).forEach(function(a) {
                        a.setState("hover")
                    });
                    e.hoverPoint && e.hoverPoint.firePointEvent("mouseOut");
                    if (!l.series) return;
                    l.firePointEvent("mouseOver");
                    e.hoverPoints = u;
                    e.hoverPoint = l;
                    g && g.refresh(p ? u: l, b)
                } else a && g && !g.isHidden && (l = g.getAnchor([{}], b), g.updatePosition({
                    plotX: l[0],
                    plotY: l[1]
                }));
                this.unDocMouseMove || (this.unDocMouseMove = E(e.container.ownerDocument, "mousemove",
                function(a) {
                    var b = c[f.hoverChartIndex];
                    if (b) b.pointer.onDocumentMouseMove(a)
                }));
                e.axes.forEach(function(a) {
                    var c = k(a.crosshair.snap, !0),
                    e = c ? L(u,
                    function(b) {
                        return b.series[a.coll] === a
                    }) : void 0;
                    e || !c ? a.drawCrosshair(b, e) : a.hideCrosshair()
                })
            };
            p.prototype.scaleGroups = function(b, a) {
                var c = this.chart,
                e;
                c.series.forEach(function(g) {
                    e = b || g.getPlotBox();
                    g.xAxis && g.xAxis.zoomEnabled && g.group && (g.group.attr(e), g.markerGroup && (g.markerGroup.attr(e), g.markerGroup.clip(a ? c.clipRect: null)), g.dataLabelsGroup && g.dataLabelsGroup.attr(e))
                });
                c.clipRect.attr(a || c.clipBox)
            };
            p.prototype.setDOMEvents = function() {
                var b = this,
                a = b.chart.container,
                c = a.ownerDocument;
                a.onmousedown = function(a) {
                    b.onContainerMouseDown(a)
                };
                a.onmousemove = function(a) {
                    b.onContainerMouseMove(a)
                };
                a.onclick = function(a) {
                    b.onContainerClick(a)
                };
                this.unbindContainerMouseLeave = E(a, "mouseleave", b.onContainerMouseLeave);
                f.unbindDocumentMouseUp || (f.unbindDocumentMouseUp = E(c, "mouseup", b.onDocumentMouseUp));
                f.hasTouch && (E(a, "touchstart",
                function(a) {
                    b.onContainerTouchStart(a)
                }), E(a, "touchmove",
                function(a) {
                    b.onContainerTouchMove(a)
                }), f.unbindDocumentTouchEnd || (f.unbindDocumentTouchEnd = E(c, "touchend", b.onDocumentTouchEnd)))
            };
            p.prototype.touch = function(b, a) {
                var c = this.chart,
                e;
                if (c.index !== f.hoverChartIndex) this.onContainerMouseLeave({
                    relatedTarget: !0
                });
                f.hoverChartIndex = c.index;
                if (1 === b.touches.length) if (b = this.normalize(b), (e = c.isInsidePlot(b.chartX - c.plotLeft, b.chartY - c.plotTop)) && !c.openMenu) {
                    a && this.runPointActions(b);
                    if ("touchmove" === b.type) {
                        a = this.pinchDown;
                        var g = a[0] ? 4 <= Math.sqrt(Math.pow(a[0].chartX - b.chartX, 2) + Math.pow(a[0].chartY - b.chartY, 2)) : !1
                    }
                    k(g, !0) && this.pinch(b)
                } else a && this.reset();
                else 2 === b.touches.length && this.pinch(b)
            };
            p.prototype.zoomOption = function(b) {
                var a = this.chart,
                c = a.options.chart,
                e = c.zoomType || "";
                a = a.inverted;
                /touch/.test(b.type) && (e = k(c.pinchType, e));
                this.zoomX = b = /x/.test(e);
                this.zoomY = e = /y/.test(e);
                this.zoomHor = b && !a || e && a;
                this.zoomVert = e && !a || b && a;
                this.hasZoom = b || e
            };
            return p
        } ();
        f.Pointer = m;
        return f.Pointer
    });
    P(y, "parts/MSPointer.js", [y["parts/Globals.js"], y["parts/Pointer.js"], y["parts/Utilities.js"]],
    function(f, m, J) {
        function r() {
            var k = [];
            k.item = function(k) {
                return this[k]
            };
            B(t,
            function(f) {
                k.push({
                    pageX: f.pageX,
                    pageY: f.pageY,
                    target: f.target
                })
            });
            return k
        }
        function E(k, n, e, c) {
            "touch" !== k.pointerType && k.pointerType !== k.MSPOINTER_TYPE_TOUCH || !z[f.hoverChartIndex] || (c(k), c = z[f.hoverChartIndex].pointer, c[n]({
                type: e,
                target: k.currentTarget,
                preventDefault: A,
                touches: r()
            }))
        }
        var N = this && this.__extends ||
        function() {
            var k = function(f, e) {
                k = Object.setPrototypeOf || {
                    __proto__: []
                }
                instanceof Array &&
                function(c, e) {
                    c.__proto__ = e
                } ||
                function(c, e) {
                    for (var g in e) e.hasOwnProperty(g) && (c[g] = e[g])
                };
                return k(f, e)
            };
            return function(f, e) {
                function c() {
                    this.constructor = f
                }
                k(f, e);
                f.prototype = null === e ? Object.create(e) : (c.prototype = e.prototype, new c)
            }
        } (),
        F = J.addEvent,
        C = J.css,
        B = J.objectEach,
        L = J.removeEvent,
        z = f.charts,
        x = f.doc,
        A = f.noop,
        t = {},
        n = !!f.win.PointerEvent;
        return function(k) {
            function f() {
                return null !== k && k.apply(this, arguments) || this
            }
            N(f, k);
            f.prototype.batchMSEvents = function(e) {
                e(this.chart.container, n ? "pointerdown": "MSPointerDown", this.onContainerPointerDown);
                e(this.chart.container, n ? "pointermove": "MSPointerMove", this.onContainerPointerMove);
                e(x, n ? "pointerup": "MSPointerUp", this.onDocumentPointerUp)
            };
            f.prototype.destroy = function() {
                this.batchMSEvents(L);
                k.prototype.destroy.call(this)
            };
            f.prototype.init = function(e, c) {
                k.prototype.init.call(this, e, c);
                this.hasZoom && C(e.container, {
                    "-ms-touch-action": "none",
                    "touch-action": "none"
                })
            };
            f.prototype.onContainerPointerDown = function(e) {
                E(e, "onContainerTouchStart", "touchstart",
                function(c) {
                    t[c.pointerId] = {
                        pageX: c.pageX,
                        pageY: c.pageY,
                        target: c.currentTarget
                    }
                })
            };
            f.prototype.onContainerPointerMove = function(e) {
                E(e, "onContainerTouchMove", "touchmove",
                function(c) {
                    t[c.pointerId] = {
                        pageX: c.pageX,
                        pageY: c.pageY
                    };
                    t[c.pointerId].target || (t[c.pointerId].target = c.currentTarget)
                })
            };
            f.prototype.onDocumentPointerUp = function(e) {
                E(e, "onDocumentTouchEnd", "touchend",
                function(c) {
                    delete t[c.pointerId]
                })
            };
            f.prototype.setDOMEvents = function() {
                k.prototype.setDOMEvents.call(this); (this.hasZoom || this.followTouchMove) && this.batchMSEvents(F)
            };
            return f
        } (m)
    });
    P(y, "parts/Legend.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.addEvent,
        r = m.css,
        E = m.defined,
        N = m.discardElement,
        F = m.find,
        C = m.fireEvent,
        B = m.format,
        L = m.isNumber,
        z = m.merge,
        x = m.pick,
        A = m.relativeLength,
        t = m.setAnimation,
        n = m.stableSort,
        k = m.syncTimeout;
        m = m.wrap;
        var q = f.isFirefox,
        e = f.marginNames,
        c = f.win,
        g = function() {
            function c(b, a) {
                this.allItems = [];
                this.contentGroup = this.box = void 0;
                this.display = !1;
                this.group = void 0;
                this.offsetWidth = this.maxLegendWidth = this.maxItemWidth = this.legendWidth = this.legendHeight = this.lastLineHeight = this.lastItemY = this.itemY = this.itemX = this.itemMarginTop = this.itemMarginBottom = this.itemHeight = this.initialItemY = 0;
                this.options = {};
                this.padding = 0;
                this.pages = [];
                this.proximate = !1;
                this.scrollGroup = void 0;
                this.widthOption = this.totalItemWidth = this.titleHeight = this.symbolWidth = this.symbolHeight = 0;
                this.chart = b;
                this.init(b, a)
            }
            c.prototype.init = function(b, a) {
                this.chart = b;
                this.setOptions(a);
                a.enabled && (this.render(), J(this.chart, "endResize",
                function() {
                    this.legend.positionCheckboxes()
                }), this.proximate ? this.unchartrender = J(this.chart, "render",
                function() {
                    this.legend.proximatePositions();
                    this.legend.positionItems()
                }) : this.unchartrender && this.unchartrender())
            };
            c.prototype.setOptions = function(b) {
                var a = x(b.padding, 8);
                this.options = b;
                this.chart.styledMode || (this.itemStyle = b.itemStyle, this.itemHiddenStyle = z(this.itemStyle, b.itemHiddenStyle));
                this.itemMarginTop = b.itemMarginTop || 0;
                this.itemMarginBottom = b.itemMarginBottom || 0;
                this.padding = a;
                this.initialItemY = a - 5;
                this.symbolWidth = x(b.symbolWidth, 16);
                this.pages = [];
                this.proximate = "proximate" === b.layout && !this.chart.inverted;
                this.baseline = void 0
            };
            c.prototype.update = function(b, a) {
                var c = this.chart;
                this.setOptions(z(!0, this.options, b));
                this.destroy();
                c.isDirtyLegend = c.isDirtyBox = !0;
                x(a, !0) && c.redraw();
                C(this, "afterUpdate")
            };
            c.prototype.colorizeItem = function(b, a) {
                b.legendGroup[a ? "removeClass": "addClass"]("highcharts-legend-item-hidden");
                if (!this.chart.styledMode) {
                    var c = this.options,
                    e = b.legendItem,
                    g = b.legendLine,
                    l = b.legendSymbol,
                    h = this.itemHiddenStyle.color;
                    c = a ? c.itemStyle.color: h;
                    var p = a ? b.color || h: h,
                    k = b.options && b.options.marker,
                    f = {
                        fill: p
                    };
                    e && e.css({
                        fill: c,
                        color: c
                    });
                    g && g.attr({
                        stroke: p
                    });
                    l && (k && l.isMarker && (f = b.pointAttribs(), a || (f.stroke = f.fill = h)), l.attr(f))
                }
                C(this, "afterColorizeItem", {
                    item: b,
                    visible: a
                })
            };
            c.prototype.positionItems = function() {
                this.allItems.forEach(this.positionItem, this);
                this.chart.isResizing || this.positionCheckboxes()
            };
            c.prototype.positionItem = function(b) {
                var a = this.options,
                c = a.symbolPadding;
                a = !a.rtl;
                var e = b._legendItemPos,
                g = e[0];
                e = e[1];
                var l = b.checkbox;
                if ((b = b.legendGroup) && b.element) b[E(b.translateY) ? "animate": "attr"]({
                    translateX: a ? g: this.legendWidth - g - 2 * c - 4,
                    translateY: e
                });
                l && (l.x = g, l.y = e)
            };
            c.prototype.destroyItem = function(b) {
                var a = b.checkbox; ["legendItem", "legendLine", "legendSymbol", "legendGroup"].forEach(function(a) {
                    b[a] && (b[a] = b[a].destroy())
                });
                a && N(b.checkbox)
            };
            c.prototype.destroy = function() {
                function b(a) {
                    this[a] && (this[a] = this[a].destroy())
                }
                this.getAllItems().forEach(function(a) { ["legendItem", "legendGroup"].forEach(b, a)
                });
                "clipRect up down pager nav box title group".split(" ").forEach(b, this);
                this.display = null
            };
            c.prototype.positionCheckboxes = function() {
                var b = this.group && this.group.alignAttr,
                a = this.clipHeight || this.legendHeight,
                c = this.titleHeight;
                if (b) {
                    var e = b.translateY;
                    this.allItems.forEach(function(g) {
                        var l = g.checkbox;
                        if (l) {
                            var h = e + c + l.y + (this.scrollOffset || 0) + 3;
                            r(l, {
                                left: b.translateX + g.checkboxOffset + l.x - 20 + "px",
                                top: h + "px",
                                display: this.proximate || h > e - 6 && h < e + a - 6 ? "": "none"
                            })
                        }
                    },
                    this)
                }
            };
            c.prototype.renderTitle = function() {
                var b = this.options,
                a = this.padding,
                c = b.title,
                e = 0;
                c.text && (this.title || (this.title = this.chart.renderer.label(c.text, a - 3, a - 4, null, null, null, b.useHTML, null, "legend-title").attr({
                    zIndex: 1
                }), this.chart.styledMode || this.title.css(c.style), this.title.add(this.group)), c.width || this.title.css({
                    width: this.maxLegendWidth + "px"
                }), b = this.title.getBBox(), e = b.height, this.offsetWidth = b.width, this.contentGroup.attr({
                    translateY: e
                }));
                this.titleHeight = e
            };
            c.prototype.setText = function(b) {
                var a = this.options;
                b.legendItem.attr({
                    text: a.labelFormat ? B(a.labelFormat, b, this.chart) : a.labelFormatter.call(b)
                })
            };
            c.prototype.renderItem = function(b) {
                var a = this.chart,
                c = a.renderer,
                e = this.options,
                g = this.symbolWidth,
                l = e.symbolPadding,
                h = this.itemStyle,
                p = this.itemHiddenStyle,
                k = "horizontal" === e.layout ? x(e.itemDistance, 20) : 0,
                f = !e.rtl,
                n = b.legendItem,
                q = !b.series,
                d = !q && b.series.drawLegendSymbol ? b.series: b,
                I = d.options;
                I = this.createCheckboxForItem && I && I.showCheckbox;
                k = g + l + k + (I ? 20 : 0);
                var O = e.useHTML,
                t = b.options.className;
                n || (b.legendGroup = c.g("legend-item").addClass("highcharts-" + d.type + "-series highcharts-color-" + b.colorIndex + (t ? " " + t: "") + (q ? " highcharts-series-" + b.index: "")).attr({
                    zIndex: 1
                }).add(this.scrollGroup), b.legendItem = n = c.text("", f ? g + l: -l, this.baseline || 0, O), a.styledMode || n.css(z(b.visible ? h: p)), n.attr({
                    align: f ? "left": "right",
                    zIndex: 2
                }).add(b.legendGroup), this.baseline || (this.fontMetrics = c.fontMetrics(a.styledMode ? 12 : h.fontSize, n), this.baseline = this.fontMetrics.f + 3 + this.itemMarginTop, n.attr("y", this.baseline)), this.symbolHeight = e.symbolHeight || this.fontMetrics.f, d.drawLegendSymbol(this, b), this.setItemEvents && this.setItemEvents(b, n, O));
                I && !b.checkbox && this.createCheckboxForItem && this.createCheckboxForItem(b);
                this.colorizeItem(b, b.visible); ! a.styledMode && h.width || n.css({
                    width: (e.itemWidth || this.widthOption || a.spacingBox.width) - k
                });
                this.setText(b);
                a = n.getBBox();
                b.itemWidth = b.checkboxOffset = e.itemWidth || b.legendItemWidth || a.width + k;
                this.maxItemWidth = Math.max(this.maxItemWidth, b.itemWidth);
                this.totalItemWidth += b.itemWidth;
                this.itemHeight = b.itemHeight = Math.round(b.legendItemHeight || a.height || this.symbolHeight)
            };
            c.prototype.layoutItem = function(b) {
                var a = this.options,
                c = this.padding,
                e = "horizontal" === a.layout,
                g = b.itemHeight,
                l = this.itemMarginBottom,
                h = this.itemMarginTop,
                p = e ? x(a.itemDistance, 20) : 0,
                k = this.maxLegendWidth;
                a = a.alignColumns && this.totalItemWidth > k ? this.maxItemWidth: b.itemWidth;
                e && this.itemX - c + a > k && (this.itemX = c, this.lastLineHeight && (this.itemY += h + this.lastLineHeight + l), this.lastLineHeight = 0);
                this.lastItemY = h + this.itemY + l;
                this.lastLineHeight = Math.max(g, this.lastLineHeight);
                b._legendItemPos = [this.itemX, this.itemY];
                e ? this.itemX += a: (this.itemY += h + g + l, this.lastLineHeight = g);
                this.offsetWidth = this.widthOption || Math.max((e ? this.itemX - c - (b.checkbox ? 0 : p) : a) + c, this.offsetWidth)
            };
            c.prototype.getAllItems = function() {
                var b = [];
                this.chart.series.forEach(function(a) {
                    var c = a && a.options;
                    a && x(c.showInLegend, E(c.linkedTo) ? !1 : void 0, !0) && (b = b.concat(a.legendItems || ("point" === c.legendType ? a.data: a)))
                });
                C(this, "afterGetAllItems", {
                    allItems: b
                });
                return b
            };
            c.prototype.getAlignment = function() {
                var b = this.options;
                return this.proximate ? b.align.charAt(0) + "tv": b.floating ? "": b.align.charAt(0) + b.verticalAlign.charAt(0) + b.layout.charAt(0)
            };
            c.prototype.adjustMargins = function(b, a) {
                var c = this.chart,
                g = this.options,
                p = this.getAlignment();
                p && [/(lth|ct|rth)/, /(rtv|rm|rbv)/, /(rbh|cb|lbh)/, /(lbv|lm|ltv)/].forEach(function(l, h) {
                    l.test(p) && !E(b[h]) && (c[e[h]] = Math.max(c[e[h]], c.legend[(h + 1) % 2 ? "legendHeight": "legendWidth"] + [1, -1, -1, 1][h] * g[h % 2 ? "x": "y"] + x(g.margin, 12) + a[h] + (c.titleOffset[h] || 0)))
                })
            };
            c.prototype.proximatePositions = function() {
                var b = this.chart,
                a = [],
                c = "left" === this.options.align;
                this.allItems.forEach(function(e) {
                    var g = c;
                    if (e.yAxis && e.points) {
                        e.xAxis.options.reversed && (g = !g);
                        var l = F(g ? e.points: e.points.slice(0).reverse(),
                        function(a) {
                            return L(a.plotY)
                        });
                        g = this.itemMarginTop + e.legendItem.getBBox().height + this.itemMarginBottom;
                        var h = e.yAxis.top - b.plotTop;
                        e.visible ? (l = l ? l.plotY: e.yAxis.height, l += h - .3 * g) : l = h + e.yAxis.height;
                        a.push({
                            target: l,
                            size: g,
                            item: e
                        })
                    }
                },
                this);
                f.distribute(a, b.plotHeight);
                a.forEach(function(a) {
                    a.item._legendItemPos[1] = b.plotTop - b.spacing[0] + a.pos
                })
            };
            c.prototype.render = function() {
                var b = this.chart,
                a = b.renderer,
                c = this.group,
                e, g = this.box,
                l = this.options,
                h = this.padding;
                this.itemX = h;
                this.itemY = this.initialItemY;
                this.lastItemY = this.offsetWidth = 0;
                this.widthOption = A(l.width, b.spacingBox.width - h);
                var p = b.spacingBox.width - 2 * h - l.x; - 1 < ["rm", "lm"].indexOf(this.getAlignment().substring(0, 2)) && (p /= 2);
                this.maxLegendWidth = this.widthOption || p;
                c || (this.group = c = a.g("legend").attr({
                    zIndex: 7
                }).add(), this.contentGroup = a.g().attr({
                    zIndex: 1
                }).add(c), this.scrollGroup = a.g().add(this.contentGroup));
                this.renderTitle();
                p = this.getAllItems();
                n(p,
                function(a, b) {
                    return (a.options && a.options.legendIndex || 0) - (b.options && b.options.legendIndex || 0)
                });
                l.reversed && p.reverse();
                this.allItems = p;
                this.display = e = !!p.length;
                this.itemHeight = this.totalItemWidth = this.maxItemWidth = this.lastLineHeight = 0;
                p.forEach(this.renderItem, this);
                p.forEach(this.layoutItem, this);
                p = (this.widthOption || this.offsetWidth) + h;
                var k = this.lastItemY + this.lastLineHeight + this.titleHeight;
                k = this.handleOverflow(k);
                k += h;
                g || (this.box = g = a.rect().addClass("highcharts-legend-box").attr({
                    r: l.borderRadius
                }).add(c), g.isNew = !0);
                b.styledMode || g.attr({
                    stroke: l.borderColor,
                    "stroke-width": l.borderWidth || 0,
                    fill: l.backgroundColor || "none"
                }).shadow(l.shadow);
                0 < p && 0 < k && (g[g.isNew ? "attr": "animate"](g.crisp.call({},
                {
                    x: 0,
                    y: 0,
                    width: p,
                    height: k
                },
                g.strokeWidth())), g.isNew = !1);
                g[e ? "show": "hide"]();
                b.styledMode && "none" === c.getStyle("display") && (p = k = 0);
                this.legendWidth = p;
                this.legendHeight = k;
                e && (a = b.spacingBox, g = a.y, /(lth|ct|rth)/.test(this.getAlignment()) && 0 < b.titleOffset[0] ? g += b.titleOffset[0] : /(lbh|cb|rbh)/.test(this.getAlignment()) && 0 < b.titleOffset[2] && (g -= b.titleOffset[2]), g !== a.y && (a = z(a, {
                    y: g
                })), c.align(z(l, {
                    width: p,
                    height: k,
                    verticalAlign: this.proximate ? "top": l.verticalAlign
                }), !0, a));
                this.proximate || this.positionItems();
                C(this, "afterRender")
            };
            c.prototype.handleOverflow = function(b) {
                var a = this,
                c = this.chart,
                e = c.renderer,
                g = this.options,
                l = g.y,
                h = this.padding;
                l = c.spacingBox.height + ("top" === g.verticalAlign ? -l: l) - h;
                var p = g.maxHeight,
                k, f = this.clipRect,
                n = g.navigation,
                q = x(n.animation, !0),
                d = n.arrowSize || 12,
                I = this.nav,
                O = this.pages,
                t,
                z = this.allItems,
                m = function(d) {
                    "number" === typeof d ? f.attr({
                        height: d
                    }) : f && (a.clipRect = f.destroy(), a.contentGroup.clip());
                    a.contentGroup.div && (a.contentGroup.div.style.clip = d ? "rect(" + h + "px,9999px," + (h + d) + "px,0)": "auto")
                },
                A = function(b) {
                    a[b] = e.circle(0, 0, 1.3 * d).translate(d / 2, d / 2).add(I);
                    c.styledMode || a[b].attr("fill", "rgba(0,0,0,0.0001)");
                    return a[b]
                };
                "horizontal" !== g.layout || "middle" === g.verticalAlign || g.floating || (l /= 2);
                p && (l = Math.min(l, p));
                O.length = 0;
                b > l && !1 !== n.enabled ? (this.clipHeight = k = Math.max(l - 20 - this.titleHeight - h, 0), this.currentPage = x(this.currentPage, 1), this.fullHeight = b, z.forEach(function(d, a) {
                    var b = d._legendItemPos[1],
                    c = Math.round(d.legendItem.getBBox().height),
                    e = O.length;
                    if (!e || b - O[e - 1] > k && (t || b) !== O[e - 1]) O.push(t || b),
                    e++;
                    d.pageIx = e - 1;
                    t && (z[a - 1].pageIx = e - 1);
                    a === z.length - 1 && b + c - O[e - 1] > k && b !== t && (O.push(b), d.pageIx = e);
                    b !== t && (t = b)
                }), f || (f = a.clipRect = e.clipRect(0, h, 9999, 0), a.contentGroup.clip(f)), m(k), I || (this.nav = I = e.g().attr({
                    zIndex: 1
                }).add(this.group), this.up = e.symbol("triangle", 0, 0, d, d).add(I), A("upTracker").on("click",
                function() {
                    a.scroll( - 1, q)
                }), this.pager = e.text("", 15, 10).addClass("highcharts-legend-navigation"), c.styledMode || this.pager.css(n.style), this.pager.add(I), this.down = e.symbol("triangle-down", 0, 0, d, d).add(I), A("downTracker").on("click",
                function() {
                    a.scroll(1, q)
                })), a.scroll(0), b = l) : I && (m(), this.nav = I.destroy(), this.scrollGroup.attr({
                    translateY: 1
                }), this.clipHeight = 0);
                return b
            };
            c.prototype.scroll = function(b, a) {
                var c = this,
                e = this.chart,
                g = this.pages,
                l = g.length,
                h = this.currentPage + b;
                b = this.clipHeight;
                var p = this.options.navigation,
                n = this.pager,
                q = this.padding;
                h > l && (h = l);
                0 < h && ("undefined" !== typeof a && t(a, e), this.nav.attr({
                    translateX: q,
                    translateY: b + this.padding + 7 + this.titleHeight,
                    visibility: "visible"
                }), [this.up, this.upTracker].forEach(function(a) {
                    a.attr({
                        "class": 1 === h ? "highcharts-legend-nav-inactive": "highcharts-legend-nav-active"
                    })
                }), n.attr({
                    text: h + "/" + l
                }), [this.down, this.downTracker].forEach(function(a) {
                    a.attr({
                        x: 18 + this.pager.getBBox().width,
                        "class": h === l ? "highcharts-legend-nav-inactive": "highcharts-legend-nav-active"
                    })
                },
                this), e.styledMode || (this.up.attr({
                    fill: 1 === h ? p.inactiveColor: p.activeColor
                }), this.upTracker.css({
                    cursor: 1 === h ? "default": "pointer"
                }), this.down.attr({
                    fill: h === l ? p.inactiveColor: p.activeColor
                }), this.downTracker.css({
                    cursor: h === l ? "default": "pointer"
                })), this.scrollOffset = -g[h - 1] + this.initialItemY, this.scrollGroup.animate({
                    translateY: this.scrollOffset
                }), this.currentPage = h, this.positionCheckboxes(), a = f.animObject(x(a, e.renderer.globalAnimation, !0)), k(function() {
                    C(c, "afterScroll", {
                        currentPage: h
                    })
                },
                a.duration || 0))
            };
            return c
        } (); (/Trident\/7\.0/.test(c.navigator && c.navigator.userAgent) || q) && m(g.prototype, "positionItem",
        function(c, b) {
            var a = this,
            e = function() {
                b._legendItemPos && c.call(a, b)
            };
            e();
            a.bubbleLegend || setTimeout(e)
        });
        f.Legend = g;
        return f.Legend
    });
    P(y, "parts/Chart.js", [y["parts/Globals.js"], y["parts/Legend.js"], y["parts/MSPointer.js"], y["parts/Pointer.js"], y["parts/Time.js"], y["parts/Utilities.js"]],
    function(f, m, J, r, E, N) {
        var F = N.addEvent,
        C = N.animate,
        B = N.animObject,
        L = N.attr,
        z = N.createElement,
        x = N.css,
        A = N.defined,
        t = N.discardElement,
        n = N.erase,
        k = N.error,
        q = N.extend,
        e = N.find,
        c = N.fireEvent,
        g = N.getStyle,
        p = N.isArray,
        b = N.isFunction,
        a = N.isNumber,
        v = N.isObject,
        D = N.isString,
        w = N.merge,
        l = N.numberFormat,
        h = N.objectEach,
        u = N.pick,
        H = N.pInt,
        M = N.relativeLength,
        Q = N.removeEvent,
        G = N.setAnimation,
        d = N.splat,
        I = N.syncTimeout,
        O = N.uniqueKey,
        T = f.doc,
        V = f.Axis,
        W = f.defaultOptions,
        R = f.charts,
        K = f.marginNames,
        X = f.seriesTypes,
        y = f.win,
        Z = f.Chart = function() {
            this.getArgs.apply(this, arguments)
        };
        f.chart = function(a, d, b) {
            return new Z(a, d, b)
        };
        q(Z.prototype, {
            callbacks: [],
            getArgs: function() {
                var a = [].slice.call(arguments);
                if (D(a[0]) || a[0].nodeName) this.renderTo = a.shift();
                this.init(a[0], a[1])
            },
            init: function(a, d) {
                var e, g = a.series,
                p = a.plotOptions || {};
                c(this, "init", {
                    args: arguments
                },
                function() {
                    a.series = null;
                    e = w(W, a);
                    h(e.plotOptions,
                    function(a, d) {
                        v(a) && (a.tooltip = p[d] && w(p[d].tooltip) || void 0)
                    });
                    e.tooltip.userOptions = a.chart && a.chart.forExport && a.tooltip.userOptions || a.tooltip;
                    e.series = a.series = g;
                    this.userOptions = a;
                    var k = e.chart,
                    u = k.events;
                    this.margin = [];
                    this.spacing = [];
                    this.bounds = {
                        h: {},
                        v: {}
                    };
                    this.labelCollectors = [];
                    this.callback = d;
                    this.isResizing = 0;
                    this.options = e;
                    this.axes = [];
                    this.series = [];
                    this.time = a.time && Object.keys(a.time).length ? new E(a.time) : f.time;
                    this.numberFormatter = k.numberFormatter || l;
                    this.styledMode = k.styledMode;
                    this.hasCartesianSeries = k.showAxes;
                    var n = this;
                    n.index = R.length;
                    R.push(n);
                    f.chartCount++;
                    u && h(u,
                    function(a, d) {
                        b(a) && F(n, d, a)
                    });
                    n.xAxis = [];
                    n.yAxis = [];
                    n.pointCount = n.colorCounter = n.symbolCounter = 0;
                    c(n, "afterInit");
                    n.firstRender()
                })
            },
            initSeries: function(a) {
                var d = this.options.chart;
                d = a.type || d.type || d.defaultSeriesType;
                var b = X[d];
                b || k(17, !0, this, {
                    missingModuleFor: d
                });
                d = new b;
                d.init(this, a);
                return d
            },
            setSeriesData: function() {
                this.getSeriesOrderByLinks().forEach(function(a) {
                    a.points || a.data || !a.enabledDataSorting || a.setData(a.options.data, !1)
                })
            },
            getSeriesOrderByLinks: function() {
                return this.series.concat().sort(function(a, d) {
                    return a.linkedSeries.length || d.linkedSeries.length ? d.linkedSeries.length - a.linkedSeries.length: 0
                })
            },
            orderSeries: function(a) {
                var d = this.series;
                for (a = a || 0; a < d.length; a++) d[a] && (d[a].index = a, d[a].name = d[a].getName())
            },
            isInsidePlot: function(a, d, b) {
                var e = b ? d: a;
                a = b ? a: d;
                e = {
                    x: e,
                    y: a,
                    isInsidePlot: 0 <= e && e <= this.plotWidth && 0 <= a && a <= this.plotHeight
                };
                c(this, "afterIsInsidePlot", e);
                return e.isInsidePlot
            },
            redraw: function(a) {
                c(this, "beforeRedraw");
                var d = this.axes,
                b = this.series,
                e = this.pointer,
                h = this.legend,
                g = this.userOptions.legend,
                l = this.isDirtyLegend,
                p = this.hasCartesianSeries,
                k = this.isDirtyBox,
                u = this.renderer,
                f = u.isHidden(),
                n = [];
                this.setResponsive && this.setResponsive(!1);
                G(this.hasRendered ? a: !1, this);
                f && this.temporaryDisplay();
                this.layOutTitles();
                for (a = b.length; a--;) {
                    var I = b[a];
                    if (I.options.stacking) {
                        var v = !0;
                        if (I.isDirty) {
                            var w = !0;
                            break
                        }
                    }
                }
                if (w) for (a = b.length; a--;) I = b[a],
                I.options.stacking && (I.isDirty = !0);
                b.forEach(function(a) {
                    a.isDirty && ("point" === a.options.legendType ? (a.updateTotals && a.updateTotals(), l = !0) : g && (g.labelFormatter || g.labelFormat) && (l = !0));
                    a.isDirtyData && c(a, "updatedData")
                });
                l && h && h.options.enabled && (h.render(), this.isDirtyLegend = !1);
                v && this.getStacks();
                p && d.forEach(function(a) {
                    a.updateNames();
                    a.setScale()
                });
                this.getMargins();
                p && (d.forEach(function(a) {
                    a.isDirty && (k = !0)
                }), d.forEach(function(a) {
                    var d = a.min + "," + a.max;
                    a.extKey !== d && (a.extKey = d, n.push(function() {
                        c(a, "afterSetExtremes", q(a.eventArgs, a.getExtremes()));
                        delete a.eventArgs
                    })); (k || v) && a.redraw()
                }));
                k && this.drawChartBox();
                c(this, "predraw");
                b.forEach(function(a) { (k || a.isDirty) && a.visible && a.redraw();
                    a.isDirtyData = !1
                });
                e && e.reset(!0);
                u.draw();
                c(this, "redraw");
                c(this, "render");
                f && this.temporaryDisplay(!0);
                n.forEach(function(a) {
                    a.call()
                })
            },
            get: function(a) {
                function d(d) {
                    return d.id === a || d.options && d.options.id === a
                }
                var b = this.series,
                c;
                var h = e(this.axes, d) || e(this.series, d);
                for (c = 0; ! h && c < b.length; c++) h = e(b[c].points || [], d);
                return h
            },
            getAxes: function() {
                var a = this,
                b = this.options,
                e = b.xAxis = d(b.xAxis || {});
                b = b.yAxis = d(b.yAxis || {});
                c(this, "getAxes");
                e.forEach(function(a, d) {
                    a.index = d;
                    a.isX = !0
                });
                b.forEach(function(a, d) {
                    a.index = d
                });
                e.concat(b).forEach(function(d) {
                    new V(a, d)
                });
                c(this, "afterGetAxes")
            },
            getSelectedPoints: function() {
                var a = [];
                this.series.forEach(function(d) {
                    a = a.concat(d.getPointsCollection().filter(function(a) {
                        return u(a.selectedStaging, a.selected)
                    }))
                });
                return a
            },
            getSelectedSeries: function() {
                return this.series.filter(function(a) {
                    return a.selected
                })
            },
            setTitle: function(a, d, b) {
                this.applyDescription("title", a);
                this.applyDescription("subtitle", d);
                this.applyDescription("caption", void 0);
                this.layOutTitles(b)
            },
            applyDescription: function(a, d) {
                var b = this,
                c = "title" === a ? {
                    color: "#333333",
                    fontSize: this.options.isStock ? "16px": "18px"
                }: {
                    color: "#666666"
                };
                c = this.options[a] = w(!this.styledMode && {
                    style: c
                },
                this.options[a], d);
                var e = this[a];
                e && d && (this[a] = e = e.destroy());
                c && !e && (e = this.renderer.text(c.text, 0, 0, c.useHTML).attr({
                    align: c.align,
                    "class": "highcharts-" + a,
                    zIndex: c.zIndex || 4
                }).add(), e.update = function(d) {
                    b[{
                        title: "setTitle",
                        subtitle: "setSubtitle",
                        caption: "setCaption"
                    } [a]](d)
                },
                this.styledMode || e.css(c.style), this[a] = e)
            },
            layOutTitles: function(a) {
                var d = [0, 0, 0],
                b = this.renderer,
                e = this.spacingBox; ["title", "subtitle", "caption"].forEach(function(a) {
                    var c = this[a],
                    h = this.options[a],
                    g = h.verticalAlign || "top";
                    a = "title" === a ? -3 : "top" === g ? d[0] + 2 : 0;
                    if (c) {
                        if (!this.styledMode) var l = h.style.fontSize;
                        l = b.fontMetrics(l, c).b;
                        c.css({
                            width: (h.width || e.width + (h.widthAdjust || 0)) + "px"
                        });
                        var p = Math.round(c.getBBox(h.useHTML).height);
                        c.align(q({
                            y: "bottom" === g ? l: a + l,
                            height: p
                        },
                        h), !1, "spacingBox");
                        h.floating || ("top" === g ? d[0] = Math.ceil(d[0] + p) : "bottom" === g && (d[2] = Math.ceil(d[2] + p)))
                    }
                },
                this);
                d[0] && "top" === (this.options.title.verticalAlign || "top") && (d[0] += this.options.title.margin);
                d[2] && "bottom" === this.options.caption.verticalAlign && (d[2] += this.options.caption.margin);
                var h = !this.titleOffset || this.titleOffset.join(",") !== d.join(",");
                this.titleOffset = d;
                c(this, "afterLayOutTitles"); ! this.isDirtyBox && h && (this.isDirtyBox = this.isDirtyLegend = h, this.hasRendered && u(a, !0) && this.isDirtyBox && this.redraw())
            },
            getChartSize: function() {
                var a = this.options.chart,
                d = a.width;
                a = a.height;
                var b = this.renderTo;
                A(d) || (this.containerWidth = g(b, "width"));
                A(a) || (this.containerHeight = g(b, "height"));
                this.chartWidth = Math.max(0, d || this.containerWidth || 600);
                this.chartHeight = Math.max(0, M(a, this.chartWidth) || (1 < this.containerHeight ? this.containerHeight: 400))
            },
            temporaryDisplay: function(a) {
                var d = this.renderTo;
                if (a) for (; d && d.style;) d.hcOrigStyle && (x(d, d.hcOrigStyle), delete d.hcOrigStyle),
                d.hcOrigDetached && (T.body.removeChild(d), d.hcOrigDetached = !1),
                d = d.parentNode;
                else for (; d && d.style;) {
                    T.body.contains(d) || d.parentNode || (d.hcOrigDetached = !0, T.body.appendChild(d));
                    if ("none" === g(d, "display", !1) || d.hcOricDetached) d.hcOrigStyle = {
                        display: d.style.display,
                        height: d.style.height,
                        overflow: d.style.overflow
                    },
                    a = {
                        display: "block",
                        overflow: "hidden"
                    },
                    d !== this.renderTo && (a.height = 0),
                    x(d, a),
                    d.offsetWidth || d.style.setProperty("display", "block", "important");
                    d = d.parentNode;
                    if (d === T.body) break
                }
            },
            setClassName: function(a) {
                this.container.className = "highcharts-container " + (a || "")
            },
            getContainer: function() {
                var d = this.options,
                b = d.chart;
                var e = this.renderTo;
                var h = O(),
                g,
                l;
                e || (this.renderTo = e = b.renderTo);
                D(e) && (this.renderTo = e = T.getElementById(e));
                e || k(13, !0, this);
                var p = H(L(e, "data-highcharts-chart"));
                a(p) && R[p] && R[p].hasRendered && R[p].destroy();
                L(e, "data-highcharts-chart", this.index);
                e.innerHTML = "";
                b.skipClone || e.offsetWidth || this.temporaryDisplay();
                this.getChartSize();
                p = this.chartWidth;
                var u = this.chartHeight;
                x(e, {
                    overflow: "hidden"
                });
                this.styledMode || (g = q({
                    position: "relative",
                    overflow: "hidden",
                    width: p + "px",
                    height: u + "px",
                    textAlign: "left",
                    lineHeight: "normal",
                    zIndex: 0,
                    "-webkit-tap-highlight-color": "rgba(0,0,0,0)"
                },
                b.style));
                this.container = e = z("div", {
                    id: h
                },
                g, e);
                this._cursor = e.style.cursor;
                this.renderer = new(f[b.renderer] || f.Renderer)(e, p, u, null, b.forExport, d.exporting && d.exporting.allowHTML, this.styledMode);
                G(void 0, this);
                this.setClassName(b.className);
                if (this.styledMode) for (l in d.defs) this.renderer.definition(d.defs[l]);
                else this.renderer.setStyle(b.style);
                this.renderer.chartIndex = this.index;
                c(this, "afterGetContainer")
            },
            getMargins: function(a) {
                var d = this.spacing,
                b = this.margin,
                e = this.titleOffset;
                this.resetMargins();
                e[0] && !A(b[0]) && (this.plotTop = Math.max(this.plotTop, e[0] + d[0]));
                e[2] && !A(b[2]) && (this.marginBottom = Math.max(this.marginBottom, e[2] + d[2]));
                this.legend && this.legend.display && this.legend.adjustMargins(b, d);
                c(this, "getMargins");
                a || this.getAxisMargins()
            },
            getAxisMargins: function() {
                var a = this,
                d = a.axisOffset = [0, 0, 0, 0],
                b = a.colorAxis,
                c = a.margin,
                e = function(a) {
                    a.forEach(function(a) {
                        a.visible && a.getOffset()
                    })
                };
                a.hasCartesianSeries ? e(a.axes) : b && b.length && e(b);
                K.forEach(function(b, e) {
                    A(c[e]) || (a[b] += d[e])
                });
                a.setChartSize()
            },
            reflow: function(a) {
                var d = this,
                b = d.options.chart,
                c = d.renderTo,
                e = A(b.width) && A(b.height),
                h = b.width || g(c, "width");
                b = b.height || g(c, "height");
                c = a ? a.target: y;
                if (!e && !d.isPrinting && h && b && (c === y || c === T)) {
                    if (h !== d.containerWidth || b !== d.containerHeight) N.clearTimeout(d.reflowTimeout),
                    d.reflowTimeout = I(function() {
                        d.container && d.setSize(void 0, void 0, !1)
                    },
                    a ? 100 : 0);
                    d.containerWidth = h;
                    d.containerHeight = b
                }
            },
            setReflow: function(a) {
                var d = this; ! 1 === a || this.unbindReflow ? !1 === a && this.unbindReflow && (this.unbindReflow = this.unbindReflow()) : (this.unbindReflow = F(y, "resize",
                function(a) {
                    d.options && d.reflow(a)
                }), F(this, "destroy", this.unbindReflow))
            },
            setSize: function(a, d, b) {
                var e = this,
                h = e.renderer;
                e.isResizing += 1;
                G(b, e);
                b = h.globalAnimation;
                e.oldChartHeight = e.chartHeight;
                e.oldChartWidth = e.chartWidth;
                "undefined" !== typeof a && (e.options.chart.width = a);
                "undefined" !== typeof d && (e.options.chart.height = d);
                e.getChartSize();
                e.styledMode || (b ? C: x)(e.container, {
                    width: e.chartWidth + "px",
                    height: e.chartHeight + "px"
                },
                b);
                e.setChartSize(!0);
                h.setSize(e.chartWidth, e.chartHeight, b);
                e.axes.forEach(function(a) {
                    a.isDirty = !0;
                    a.setScale()
                });
                e.isDirtyLegend = !0;
                e.isDirtyBox = !0;
                e.layOutTitles();
                e.getMargins();
                e.redraw(b);
                e.oldChartHeight = null;
                c(e, "resize");
                I(function() {
                    e && c(e, "endResize", null,
                    function() {--e.isResizing
                    })
                },
                B(b).duration || 0)
            },
            setChartSize: function(a) {
                var d = this.inverted,
                b = this.renderer,
                e = this.chartWidth,
                h = this.chartHeight,
                g = this.options.chart,
                l = this.spacing,
                p = this.clipOffset,
                k, u, f, n;
                this.plotLeft = k = Math.round(this.plotLeft);
                this.plotTop = u = Math.round(this.plotTop);
                this.plotWidth = f = Math.max(0, Math.round(e - k - this.marginRight));
                this.plotHeight = n = Math.max(0, Math.round(h - u - this.marginBottom));
                this.plotSizeX = d ? n: f;
                this.plotSizeY = d ? f: n;
                this.plotBorderWidth = g.plotBorderWidth || 0;
                this.spacingBox = b.spacingBox = {
                    x: l[3],
                    y: l[0],
                    width: e - l[3] - l[1],
                    height: h - l[0] - l[2]
                };
                this.plotBox = b.plotBox = {
                    x: k,
                    y: u,
                    width: f,
                    height: n
                };
                e = 2 * Math.floor(this.plotBorderWidth / 2);
                d = Math.ceil(Math.max(e, p[3]) / 2);
                b = Math.ceil(Math.max(e, p[0]) / 2);
                this.clipBox = {
                    x: d,
                    y: b,
                    width: Math.floor(this.plotSizeX - Math.max(e, p[1]) / 2 - d),
                    height: Math.max(0, Math.floor(this.plotSizeY - Math.max(e, p[2]) / 2 - b))
                };
                a || this.axes.forEach(function(a) {
                    a.setAxisSize();
                    a.setAxisTranslation()
                });
                c(this, "afterSetChartSize", {
                    skipAxes: a
                })
            },
            resetMargins: function() {
                c(this, "resetMargins");
                var a = this,
                d = a.options.chart; ["margin", "spacing"].forEach(function(b) {
                    var c = d[b],
                    e = v(c) ? c: [c, c, c, c]; ["Top", "Right", "Bottom", "Left"].forEach(function(c, h) {
                        a[b][h] = u(d[b + c], e[h])
                    })
                });
                K.forEach(function(d, b) {
                    a[d] = u(a.margin[b], a.spacing[b])
                });
                a.axisOffset = [0, 0, 0, 0];
                a.clipOffset = [0, 0, 0, 0]
            },
            drawChartBox: function() {
                var a = this.options.chart,
                d = this.renderer,
                b = this.chartWidth,
                e = this.chartHeight,
                h = this.chartBackground,
                g = this.plotBackground,
                l = this.plotBorder,
                p = this.styledMode,
                k = this.plotBGImage,
                u = a.backgroundColor,
                f = a.plotBackgroundColor,
                n = a.plotBackgroundImage,
                I, v = this.plotLeft,
                q = this.plotTop,
                w = this.plotWidth,
                O = this.plotHeight,
                H = this.plotBox,
                G = this.clipRect,
                t = this.clipBox,
                x = "animate";
                h || (this.chartBackground = h = d.rect().addClass("highcharts-background").add(), x = "attr");
                if (p) var D = I = h.strokeWidth();
                else {
                    D = a.borderWidth || 0;
                    I = D + (a.shadow ? 8 : 0);
                    u = {
                        fill: u || "none"
                    };
                    if (D || h["stroke-width"]) u.stroke = a.borderColor,
                    u["stroke-width"] = D;
                    h.attr(u).shadow(a.shadow)
                }
                h[x]({
                    x: I / 2,
                    y: I / 2,
                    width: b - I - D % 2,
                    height: e - I - D % 2,
                    r: a.borderRadius
                });
                x = "animate";
                g || (x = "attr", this.plotBackground = g = d.rect().addClass("highcharts-plot-background").add());
                g[x](H);
                p || (g.attr({
                    fill: f || "none"
                }).shadow(a.plotShadow), n && (k ? (n !== k.attr("href") && k.attr("href", n), k.animate(H)) : this.plotBGImage = d.image(n, v, q, w, O).add()));
                G ? G.animate({
                    width: t.width,
                    height: t.height
                }) : this.clipRect = d.clipRect(t);
                x = "animate";
                l || (x = "attr", this.plotBorder = l = d.rect().addClass("highcharts-plot-border").attr({
                    zIndex: 1
                }).add());
                p || l.attr({
                    stroke: a.plotBorderColor,
                    "stroke-width": a.plotBorderWidth || 0,
                    fill: "none"
                });
                l[x](l.crisp({
                    x: v,
                    y: q,
                    width: w,
                    height: O
                },
                -l.strokeWidth()));
                this.isDirtyBox = !1;
                c(this, "afterDrawChartBox")
            },
            propFromSeries: function() {
                var a = this,
                d = a.options.chart,
                b, c = a.options.series,
                e, h; ["inverted", "angular", "polar"].forEach(function(g) {
                    b = X[d.type || d.defaultSeriesType];
                    h = d[g] || b && b.prototype[g];
                    for (e = c && c.length; ! h && e--;)(b = X[c[e].type]) && b.prototype[g] && (h = !0);
                    a[g] = h
                })
            },
            linkSeries: function() {
                var a = this,
                d = a.series;
                d.forEach(function(a) {
                    a.linkedSeries.length = 0
                });
                d.forEach(function(d) {
                    var b = d.options.linkedTo;
                    D(b) && (b = ":previous" === b ? a.series[d.index - 1] : a.get(b)) && b.linkedParent !== d && (b.linkedSeries.push(d), d.linkedParent = b, b.enabledDataSorting && d.setDataSortingOptions(), d.visible = u(d.options.visible, b.options.visible, d.visible))
                });
                c(this, "afterLinkSeries")
            },
            renderSeries: function() {
                this.series.forEach(function(a) {
                    a.translate();
                    a.render()
                })
            },
            renderLabels: function() {
                var a = this,
                d = a.options.labels;
                d.items && d.items.forEach(function(b) {
                    var c = q(d.style, b.style),
                    e = H(c.left) + a.plotLeft,
                    h = H(c.top) + a.plotTop + 12;
                    delete c.left;
                    delete c.top;
                    a.renderer.text(b.html, e, h).attr({
                        zIndex: 2
                    }).css(c).add()
                })
            },
            render: function() {
                var a = this.axes,
                d = this.colorAxis,
                b = this.renderer,
                c = this.options,
                e = 0,
                h = function(a) {
                    a.forEach(function(a) {
                        a.visible && a.render()
                    })
                };
                this.setTitle();
                this.legend = new m(this, c.legend);
                this.getStacks && this.getStacks();
                this.getMargins(!0);
                this.setChartSize();
                c = this.plotWidth;
                a.some(function(a) {
                    if (a.horiz && a.visible && a.options.labels.enabled && a.series.length) return e = 21,
                    !0
                });
                var g = this.plotHeight = Math.max(this.plotHeight - e, 0);
                a.forEach(function(a) {
                    a.setScale()
                });
                this.getAxisMargins();
                var l = 1.1 < c / this.plotWidth;
                var p = 1.05 < g / this.plotHeight;
                if (l || p) a.forEach(function(a) { (a.horiz && l || !a.horiz && p) && a.setTickInterval(!0)
                }),
                this.getMargins();
                this.drawChartBox();
                this.hasCartesianSeries ? h(a) : d && d.length && h(d);
                this.seriesGroup || (this.seriesGroup = b.g("series-group").attr({
                    zIndex: 3
                }).add());
                this.renderSeries();
                this.renderLabels();
                this.addCredits();
                this.setResponsive && this.setResponsive();
                this.updateContainerScaling();
                this.hasRendered = !0
            },
            addCredits: function(a) {
                var d = this;
                a = w(!0, this.options.credits, a);
                a.enabled && !this.credits && (this.credits = this.renderer.text(a.text + (this.mapCredits || ""), 0, 0).addClass("highcharts-credits").on("click",
                function() {
                    a.href && (y.location.href = a.href)
                }).attr({
                    align: a.position.align,
                    zIndex: 8
                }), d.styledMode || this.credits.css(a.style), this.credits.add().align(a.position), this.credits.update = function(a) {
                    d.credits = d.credits.destroy();
                    d.addCredits(a)
                })
            },
            updateContainerScaling: function() {
                var a = this.container;
                if (a.offsetWidth && a.offsetHeight && a.getBoundingClientRect) {
                    var d = a.getBoundingClientRect(),
                    b = d.width / a.offsetWidth;
                    a = d.height / a.offsetHeight;
                    1 !== b || 1 !== a ? this.containerScaling = {
                        scaleX: b,
                        scaleY: a
                    }: delete this.containerScaling
                }
            },
            destroy: function() {
                var a = this,
                d = a.axes,
                b = a.series,
                e = a.container,
                g, l = e && e.parentNode;
                c(a, "destroy");
                a.renderer.forExport ? n(R, a) : R[a.index] = void 0;
                f.chartCount--;
                a.renderTo.removeAttribute("data-highcharts-chart");
                Q(a);
                for (g = d.length; g--;) d[g] = d[g].destroy();
                this.scroller && this.scroller.destroy && this.scroller.destroy();
                for (g = b.length; g--;) b[g] = b[g].destroy();
                "title subtitle chartBackground plotBackground plotBGImage plotBorder seriesGroup clipRect credits pointer rangeSelector legend resetZoomButton tooltip renderer".split(" ").forEach(function(d) {
                    var b = a[d];
                    b && b.destroy && (a[d] = b.destroy())
                });
                e && (e.innerHTML = "", Q(e), l && t(e));
                h(a,
                function(d, b) {
                    delete a[b]
                })
            },
            firstRender: function() {
                var a = this,
                d = a.options;
                if (!a.isReadyToRender || a.isReadyToRender()) {
                    a.getContainer();
                    a.resetMargins();
                    a.setChartSize();
                    a.propFromSeries();
                    a.getAxes(); (p(d.series) ? d.series: []).forEach(function(d) {
                        a.initSeries(d)
                    });
                    a.linkSeries();
                    a.setSeriesData();
                    c(a, "beforeRender");
                    r && (a.pointer = f.hasTouch || !y.PointerEvent && !y.MSPointerEvent ? new r(a, d) : new J(a, d));
                    a.render();
                    if (!a.renderer.imgCount && !a.hasLoaded) a.onload();
                    a.temporaryDisplay(!0)
                }
            },
            onload: function() {
                this.callbacks.concat([this.callback]).forEach(function(a) {
                    a && "undefined" !== typeof this.index && a.apply(this, [this])
                },
                this);
                c(this, "load");
                c(this, "render");
                A(this.index) && this.setReflow(this.options.chart.reflow);
                this.hasLoaded = !0
            }
        })
    });
    P(y, "parts/ScrollablePlotArea.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.addEvent,
        r = m.createElement,
        E = m.pick,
        y = m.stop;
        m = f.Chart;
        "";
        J(m, "afterSetChartSize",
        function(m) {
            var r = this.options.chart.scrollablePlotArea,
            B = r && r.minWidth;
            r = r && r.minHeight;
            if (!this.renderer.forExport) {
                if (B) {
                    if (this.scrollablePixelsX = B = Math.max(0, B - this.chartWidth)) {
                        this.plotWidth += B;
                        this.inverted ? (this.clipBox.height += B, this.plotBox.height += B) : (this.clipBox.width += B, this.plotBox.width += B);
                        var F = {
                            1 : {
                                name: "right",
                                value: B
                            }
                        }
                    }
                } else r && (this.scrollablePixelsY = B = Math.max(0, r - this.chartHeight)) && (this.plotHeight += B, this.inverted ? (this.clipBox.width += B, this.plotBox.width += B) : (this.clipBox.height += B, this.plotBox.height += B), F = {
                    2 : {
                        name: "bottom",
                        value: B
                    }
                });
                F && !m.skipAxes && this.axes.forEach(function(z) {
                    F[z.side] ? z.getPlotLinePath = function() {
                        var x = F[z.side].name,
                        m = this[x];
                        this[x] = m - F[z.side].value;
                        var t = f.Axis.prototype.getPlotLinePath.apply(this, arguments);
                        this[x] = m;
                        return t
                    }: (z.setAxisSize(), z.setAxisTranslation())
                })
            }
        });
        J(m, "render",
        function() {
            this.scrollablePixelsX || this.scrollablePixelsY ? (this.setUpScrolling && this.setUpScrolling(), this.applyFixed()) : this.fixedDiv && this.applyFixed()
        });
        m.prototype.setUpScrolling = function() {
            var f = this,
            m = {
                WebkitOverflowScrolling: "touch",
                overflowX: "hidden",
                overflowY: "hidden"
            };
            this.scrollablePixelsX && (m.overflowX = "auto");
            this.scrollablePixelsY && (m.overflowY = "auto");
            this.scrollingContainer = r("div", {
                className: "highcharts-scrolling"
            },
            m, this.renderTo);
            J(this.scrollingContainer, "scroll",
            function() {
                f.pointer && delete f.pointer.chartPosition
            });
            this.innerContainer = r("div", {
                className: "highcharts-inner-container"
            },
            null, this.scrollingContainer);
            this.innerContainer.appendChild(this.container);
            this.setUpScrolling = null
        };
        m.prototype.moveFixedElements = function() {
            var f = this.container,
            m = this.fixedRenderer,
            r = ".highcharts-contextbutton .highcharts-credits .highcharts-legend .highcharts-legend-checkbox .highcharts-navigator-series .highcharts-navigator-xaxis .highcharts-navigator-yaxis .highcharts-navigator .highcharts-reset-zoom .highcharts-scrollbar .highcharts-subtitle .highcharts-title".split(" "),
            E;
            this.scrollablePixelsX && !this.inverted ? E = ".highcharts-yaxis": this.scrollablePixelsX && this.inverted ? E = ".highcharts-xaxis": this.scrollablePixelsY && !this.inverted ? E = ".highcharts-xaxis": this.scrollablePixelsY && this.inverted && (E = ".highcharts-yaxis");
            r.push(E, E + "-labels");
            r.forEach(function(z) { [].forEach.call(f.querySelectorAll(z),
                function(f) { (f.namespaceURI === m.SVG_NS ? m.box: m.box.parentNode).appendChild(f);
                    f.style.pointerEvents = "auto"
                })
            })
        };
        m.prototype.applyFixed = function() {
            var m, C = !this.fixedDiv,
            B = this.options.chart.scrollablePlotArea;
            C ? (this.fixedDiv = r("div", {
                className: "highcharts-fixed"
            },
            {
                position: "absolute",
                overflow: "hidden",
                pointerEvents: "none",
                zIndex: 2
            },
            null, !0), this.renderTo.insertBefore(this.fixedDiv, this.renderTo.firstChild), this.renderTo.style.overflow = "visible", this.fixedRenderer = m = new f.Renderer(this.fixedDiv, this.chartWidth, this.chartHeight), this.scrollableMask = m.path().attr({
                fill: this.options.chart.backgroundColor || "#fff",
                "fill-opacity": E(B.opacity, .85),
                zIndex: -1
            }).addClass("highcharts-scrollable-mask").add(), this.moveFixedElements(), J(this, "afterShowResetZoom", this.moveFixedElements), J(this, "afterLayOutTitles", this.moveFixedElements)) : this.fixedRenderer.setSize(this.chartWidth, this.chartHeight);
            m = this.chartWidth + (this.scrollablePixelsX || 0);
            var L = this.chartHeight + (this.scrollablePixelsY || 0);
            y(this.container);
            this.container.style.width = m + "px";
            this.container.style.height = L + "px";
            this.renderer.boxWrapper.attr({
                width: m,
                height: L,
                viewBox: [0, 0, m, L].join(" ")
            });
            this.chartBackground.attr({
                width: m,
                height: L
            });
            this.scrollablePixelsY && (this.scrollingContainer.style.height = this.chartHeight + "px");
            C && (B.scrollPositionX && (this.scrollingContainer.scrollLeft = this.scrollablePixelsX * B.scrollPositionX), B.scrollPositionY && (this.scrollingContainer.scrollTop = this.scrollablePixelsY * B.scrollPositionY));
            L = this.axisOffset;
            C = this.plotTop - L[0] - 1;
            B = this.plotLeft - L[3] - 1;
            m = this.plotTop + this.plotHeight + L[2] + 1;
            L = this.plotLeft + this.plotWidth + L[1] + 1;
            var z = this.plotLeft + this.plotWidth - (this.scrollablePixelsX || 0),
            x = this.plotTop + this.plotHeight - (this.scrollablePixelsY || 0);
            C = this.scrollablePixelsX ? ["M", 0, C, "L", this.plotLeft - 1, C, "L", this.plotLeft - 1, m, "L", 0, m, "Z", "M", z, C, "L", this.chartWidth, C, "L", this.chartWidth, m, "L", z, m, "Z"] : this.scrollablePixelsY ? ["M", B, 0, "L", B, this.plotTop - 1, "L", L, this.plotTop - 1, "L", L, 0, "Z", "M", B, x, "L", B, this.chartHeight, "L", L, this.chartHeight, "L", L, x, "Z"] : ["M", 0, 0];
            "adjustHeight" !== this.redrawTrigger && this.scrollableMask.attr({
                d: C
            })
        }
    });
    P(y, "mixins/legend-symbol.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.merge,
        r = m.pick;
        f.LegendSymbolMixin = {
            drawRectangle: function(f, m) {
                var F = f.symbolHeight,
                C = f.options.squareSymbol;
                m.legendSymbol = this.chart.renderer.rect(C ? (f.symbolWidth - F) / 2 : 0, f.baseline - F + 1, C ? F: f.symbolWidth, F, r(f.options.symbolRadius, F / 2)).addClass("highcharts-point").attr({
                    zIndex: 3
                }).add(m.legendGroup)
            },
            drawLineMarker: function(f) {
                var m = this.options,
                F = m.marker,
                C = f.symbolWidth,
                B = f.symbolHeight,
                E = B / 2,
                z = this.chart.renderer,
                x = this.legendGroup;
                f = f.baseline - Math.round(.3 * f.fontMetrics.b);
                var A = {};
                this.chart.styledMode || (A = {
                    "stroke-width": m.lineWidth || 0
                },
                m.dashStyle && (A.dashstyle = m.dashStyle));
                this.legendLine = z.path(["M", 0, f, "L", C, f]).addClass("highcharts-graph").attr(A).add(x);
                F && !1 !== F.enabled && C && (m = Math.min(r(F.radius, E), E), 0 === this.symbol.indexOf("url") && (F = J(F, {
                    width: B,
                    height: B
                }), m = 0), this.legendSymbol = F = z.symbol(this.symbol, C / 2 - m, f - m, 2 * m, 2 * m, F).addClass("highcharts-point").add(x), F.isMarker = !0)
            }
        };
        return f.LegendSymbolMixin
    });
    P(y, "parts/Point.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        "";
        var J = m.animObject,
        r = m.defined,
        E = m.erase,
        y = m.extend,
        F = m.format,
        C = m.getNestedProperty,
        B = m.isArray,
        L = m.isNumber,
        z = m.isObject,
        x = m.syncTimeout,
        A = m.pick,
        t = m.removeEvent,
        n = m.uniqueKey,
        k = f.fireEvent;
        m = function() {
            function f() {
                this.colorIndex = this.category = void 0;
                this.formatPrefix = "point";
                this.id = void 0;
                this.isNull = !1;
                this.percentage = this.options = this.name = void 0;
                this.selected = !1;
                this.total = this.series = void 0;
                this.visible = !0;
                this.x = void 0
            }
            f.prototype.animateBeforeDestroy = function() {
                var e = this,
                c = {
                    x: e.startXPos,
                    opacity: 0
                },
                g,
                p = e.getGraphicalProps();
                p.singular.forEach(function(b) {
                    g = "dataLabel" === b;
                    e[b] = e[b].animate(g ? {
                        x: e[b].startXPos,
                        y: e[b].startYPos,
                        opacity: 0
                    }: c)
                });
                p.plural.forEach(function(b) {
                    e[b].forEach(function(a) {
                        a.element && a.animate(y({
                            x: e.startXPos
                        },
                        a.startYPos ? {
                            x: a.startXPos,
                            y: a.startYPos
                        }: {}))
                    })
                })
            };
            f.prototype.applyOptions = function(e, c) {
                var g = this.series,
                p = g.options.pointValKey || g.pointValKey;
                e = f.prototype.optionsToObject.call(this, e);
                y(this, e);
                this.options = this.options ? y(this.options, e) : e;
                e.group && delete this.group;
                e.dataLabels && delete this.dataLabels;
                p && (this.y = f.prototype.getNestedProperty.call(this, p));
                this.formatPrefix = (this.isNull = A(this.isValid && !this.isValid(), null === this.x || !L(this.y))) ? "null": "point";
                this.selected && (this.state = "select");
                "name" in this && "undefined" === typeof c && g.xAxis && g.xAxis.hasNames && (this.x = g.xAxis.nameToX(this));
                "undefined" === typeof this.x && g && (this.x = "undefined" === typeof c ? g.autoIncrement(this) : c);
                return this
            };
            f.prototype.destroy = function() {
                function e() {
                    if (c.graphic || c.dataLabel || c.dataLabels) t(c),
                    c.destroyElements();
                    for (k in c) c[k] = null
                }
                var c = this,
                g = c.series,
                p = g.chart;
                g = g.options.dataSorting;
                var b = p.hoverPoints,
                a = J(c.series.chart.renderer.globalAnimation),
                k;
                c.legendItem && p.legend.destroyItem(c);
                b && (c.setState(), E(b, c), b.length || (p.hoverPoints = null));
                if (c === p.hoverPoint) c.onMouseOut();
                g && g.enabled ? (this.animateBeforeDestroy(), x(e, a.duration)) : e();
                p.pointCount--
            };
            f.prototype.destroyElements = function(e) {
                var c = this;
                e = c.getGraphicalProps(e);
                e.singular.forEach(function(e) {
                    c[e] = c[e].destroy()
                });
                e.plural.forEach(function(e) {
                    c[e].forEach(function(c) {
                        c.element && c.destroy()
                    });
                    delete c[e]
                })
            };
            f.prototype.firePointEvent = function(e, c, g) {
                var p = this,
                b = this.series.options; (b.point.events[e] || p.options && p.options.events && p.options.events[e]) && p.importEvents();
                "click" === e && b.allowPointSelect && (g = function(a) {
                    p.select && p.select(null, a.ctrlKey || a.metaKey || a.shiftKey)
                });
                k(p, e, c, g)
            };
            f.prototype.getClassName = function() {
                return "highcharts-point" + (this.selected ? " highcharts-point-select": "") + (this.negative ? " highcharts-negative": "") + (this.isNull ? " highcharts-null-point": "") + ("undefined" !== typeof this.colorIndex ? " highcharts-color-" + this.colorIndex: "") + (this.options.className ? " " + this.options.className: "") + (this.zone && this.zone.className ? " " + this.zone.className.replace("highcharts-negative", "") : "")
            };
            f.prototype.getGraphicalProps = function(e) {
                var c = this,
                g = [],
                p,
                b = {
                    singular: [],
                    plural: []
                };
                e = e || {
                    graphic: 1,
                    dataLabel: 1
                };
                e.graphic && g.push("graphic", "shadowGroup");
                e.dataLabel && g.push("dataLabel", "dataLabelUpper", "connector");
                for (p = g.length; p--;) {
                    var a = g[p];
                    c[a] && b.singular.push(a)
                } ["dataLabel", "connector"].forEach(function(a) {
                    var g = a + "s";
                    e[a] && c[g] && b.plural.push(g)
                });
                return b
            };
            f.prototype.getLabelConfig = function() {
                return {
                    x: this.category,
                    y: this.y,
                    color: this.color,
                    colorIndex: this.colorIndex,
                    key: this.name || this.category,
                    series: this.series,
                    point: this,
                    percentage: this.percentage,
                    total: this.total || this.stackTotal
                }
            };
            f.prototype.getNestedProperty = function(e) {
                if (e) return 0 === e.indexOf("custom.") ? C(e, this.options) : this[e]
            };
            f.prototype.getZone = function() {
                var e = this.series,
                c = e.zones;
                e = e.zoneAxis || "y";
                var g = 0,
                p;
                for (p = c[g]; this[e] >= p.value;) p = c[++g];
                this.nonZonedColor || (this.nonZonedColor = this.color);
                this.color = p && p.color && !this.options.color ? p.color: this.nonZonedColor;
                return p
            };
            f.prototype.hasNewShapeType = function() {
                return (this.graphic && (this.graphic.symbolName || this.graphic.element.nodeName)) !== this.shapeType
            };
            f.prototype.init = function(e, c, g) {
                this.series = e;
                this.applyOptions(c, g);
                this.id = r(this.id) ? this.id: n();
                this.resolveColor();
                e.chart.pointCount++;
                k(this, "afterInit");
                return this
            };
            f.prototype.optionsToObject = function(e) {
                var c = {},
                g = this.series,
                p = g.options.keys,
                b = p || g.pointArrayMap || ["y"],
                a = b.length,
                k = 0,
                n = 0;
                if (L(e) || null === e) c[b[0]] = e;
                else if (B(e)) for (!p && e.length > a && (g = typeof e[0], "string" === g ? c.name = e[0] : "number" === g && (c.x = e[0]), k++); n < a;) p && "undefined" === typeof e[k] || (0 < b[n].indexOf(".") ? f.prototype.setNestedProperty(c, e[k], b[n]) : c[b[n]] = e[k]),
                k++,
                n++;
                else "object" === typeof e && (c = e, e.dataLabels && (g._hasPointLabels = !0), e.marker && (g._hasPointMarkers = !0));
                return c
            };
            f.prototype.resolveColor = function() {
                var e = this.series;
                var c = e.chart.options.chart.colorCount;
                var g = e.chart.styledMode;
                g || this.options.color || (this.color = e.color);
                e.options.colorByPoint ? (g || (c = e.options.colors || e.chart.options.colors, this.color = this.color || c[e.colorCounter], c = c.length), g = e.colorCounter, e.colorCounter++, e.colorCounter === c && (e.colorCounter = 0)) : g = e.colorIndex;
                this.colorIndex = A(this.colorIndex, g)
            };
            f.prototype.setNestedProperty = function(e, c, g) {
                g.split(".").reduce(function(e, b, a, g) {
                    e[b] = g.length - 1 === a ? c: z(e[b], !0) ? e[b] : {};
                    return e[b]
                },
                e);
                return e
            };
            f.prototype.tooltipFormatter = function(e) {
                var c = this.series,
                g = c.tooltipOptions,
                p = A(g.valueDecimals, ""),
                b = g.valuePrefix || "",
                a = g.valueSuffix || "";
                c.chart.styledMode && (e = c.chart.tooltip.styledModeFormat(e)); (c.pointArrayMap || ["y"]).forEach(function(c) {
                    c = "{point." + c;
                    if (b || a) e = e.replace(RegExp(c + "}", "g"), b + c + "}" + a);
                    e = e.replace(RegExp(c + "}", "g"), c + ":,." + p + "f}")
                });
                return F(e, {
                    point: this,
                    series: this.series
                },
                c.chart)
            };
            return f
        } ();
        f.Point = m;
        return f.Point
    });
    P(y, "parts/Series.js", [y["parts/Globals.js"], y["mixins/legend-symbol.js"], y["parts/Point.js"], y["parts/Utilities.js"]],
    function(f, m, J, r) {
        "";
        var E = r.addEvent,
        y = r.animObject,
        F = r.arrayMax,
        C = r.arrayMin,
        B = r.clamp,
        L = r.correctFloat,
        z = r.defined,
        x = r.erase,
        A = r.error,
        t = r.extend,
        n = r.find,
        k = r.fireEvent,
        q = r.getNestedProperty,
        e = r.isArray,
        c = r.isFunction,
        g = r.isNumber,
        p = r.isString,
        b = r.merge,
        a = r.objectEach,
        v = r.pick,
        D = r.removeEvent,
        w = r.seriesType,
        l = r.splat,
        h = r.syncTimeout,
        u = f.defaultOptions,
        H = f.defaultPlotOptions,
        M = f.seriesTypes,
        Q = f.SVGElement,
        G = f.win;
        f.Series = w("line", null, {
            lineWidth: 2,
            allowPointSelect: !1,
            showCheckbox: !1,
            animation: {
                duration: 1E3
            },
            events: {},
            marker: {
                enabledThreshold: 2,
                lineColor: "#ffffff",
                lineWidth: 0,
                radius: 4,
                states: {
                    normal: {
                        animation: !0
                    },
                    hover: {
                        animation: {
                            duration: 50
                        },
                        enabled: !0,
                        radiusPlus: 2,
                        lineWidthPlus: 1
                    },
                    select: {
                        fillColor: "#cccccc",
                        lineColor: "#000000",
                        lineWidth: 2
                    }
                }
            },
            point: {
                events: {}
            },
            dataLabels: {
                align: "center",
                formatter: function() {
                    var a = this.series.chart.numberFormatter;
                    return "number" !== typeof this.y ? "": a(this.y, -1)
                },
                padding: 5,
                style: {
                    fontSize: "11px",
                    fontWeight: "bold",
                    color: "contrast",
                    textOutline: "1px contrast"
                },
                verticalAlign: "bottom",
                x: 0,
                y: 0
            },
            cropThreshold: 300,
            opacity: 1,
            pointRange: 0,
            softThreshold: !0,
            states: {
                normal: {
                    animation: !0
                },
                hover: {
                    animation: {
                        duration: 50
                    },
                    lineWidthPlus: 1,
                    marker: {},
                    halo: {
                        size: 10,
                        opacity: .25
                    }
                },
                select: {
                    animation: {
                        duration: 0
                    }
                },
                inactive: {
                    animation: {
                        duration: 50
                    },
                    opacity: .2
                }
            },
            stickyTracking: !0,
            turboThreshold: 1E3,
            findNearestPointBy: "x"
        },
        {
            axisTypes: ["xAxis", "yAxis"],
            coll: "series",
            colorCounter: 0,
            cropShoulder: 1,
            directTouch: !1,
            eventsToUnbind: [],
            isCartesian: !0,
            parallelArrays: ["x", "y"],
            pointClass: J,
            requireSorting: !0,
            sorted: !0,
            init: function(d, b) {
                k(this, "init", {
                    options: b
                });
                var e = this,
                h = d.series,
                g;
                this.eventOptions = this.eventOptions || {};
                e.chart = d;
                e.options = b = e.setOptions(b);
                e.linkedSeries = [];
                e.bindAxes();
                t(e, {
                    name: b.name,
                    state: "",
                    visible: !1 !== b.visible,
                    selected: !0 === b.selected
                });
                var l = b.events;
                a(l,
                function(a, d) {
                    c(a) && e.eventOptions[d] !== a && (c(e.eventOptions[d]) && D(e, d, e.eventOptions[d]), e.eventOptions[d] = a, E(e, d, a))
                });
                if (l && l.click || b.point && b.point.events && b.point.events.click || b.allowPointSelect) d.runTrackerClick = !0;
                e.getColor();
                e.getSymbol();
                e.parallelArrays.forEach(function(a) {
                    e[a + "Data"] || (e[a + "Data"] = [])
                });
                e.isCartesian && (d.hasCartesianSeries = !0);
                h.length && (g = h[h.length - 1]);
                e._i = v(g && g._i, -1) + 1;
                d.orderSeries(this.insert(h));
                b.dataSorting && b.dataSorting.enabled ? e.setDataSortingOptions() : e.points || e.data || e.setData(b.data, !1);
                k(this, "afterInit")
            },
            is: function(a) {
                return M[a] && this instanceof M[a]
            },
            insert: function(a) {
                var d = this.options.index,
                b;
                if (g(d)) {
                    for (b = a.length; b--;) if (d >= v(a[b].options.index, a[b]._i)) {
                        a.splice(b + 1, 0, this);
                        break
                    } - 1 === b && a.unshift(this);
                    b += 1
                } else a.push(this);
                return v(b, a.length - 1)
            },
            bindAxes: function() {
                var a = this,
                b = a.options,
                c = a.chart,
                e;
                k(this, "bindAxes", null,
                function() { (a.axisTypes || []).forEach(function(d) {
                        c[d].forEach(function(c) {
                            e = c.options;
                            if (b[d] === e.index || "undefined" !== typeof b[d] && b[d] === e.id || "undefined" === typeof b[d] && 0 === e.index) a.insert(c.series),
                            a[d] = c,
                            c.isDirty = !0
                        });
                        a[d] || a.optionalAxis === d || A(18, !0, c)
                    })
                });
                k(this, "afterBindAxes")
            },
            updateParallelArrays: function(a, b) {
                var d = a.series,
                c = arguments,
                e = g(b) ?
                function(c) {
                    var e = "y" === c && d.toYData ? d.toYData(a) : a[c];
                    d[c + "Data"][b] = e
                }: function(a) {
                    Array.prototype[b].apply(d[a + "Data"], Array.prototype.slice.call(c, 2))
                };
                d.parallelArrays.forEach(e)
            },
            hasData: function() {
                return this.visible && "undefined" !== typeof this.dataMax && "undefined" !== typeof this.dataMin || this.visible && this.yData && 0 < this.yData.length
            },
            autoIncrement: function() {
                var a = this.options,
                b = this.xIncrement,
                c, e = a.pointIntervalUnit,
                h = this.chart.time;
                b = v(b, a.pointStart, 0);
                this.pointInterval = c = v(this.pointInterval, a.pointInterval, 1);
                e && (a = new h.Date(b), "day" === e ? h.set("Date", a, h.get("Date", a) + c) : "month" === e ? h.set("Month", a, h.get("Month", a) + c) : "year" === e && h.set("FullYear", a, h.get("FullYear", a) + c), c = a.getTime() - b);
                this.xIncrement = b + c;
                return b
            },
            setDataSortingOptions: function() {
                var a = this.options;
                t(this, {
                    requireSorting: !1,
                    sorted: !1,
                    enabledDataSorting: !0,
                    allowDG: !1
                });
                z(a.pointRange) || (a.pointRange = 1)
            },
            setOptions: function(a) {
                var d = this.chart,
                c = d.options,
                e = c.plotOptions,
                h = d.userOptions || {};
                a = b(a);
                d = d.styledMode;
                var g = {
                    plotOptions: e,
                    userOptions: a
                };
                k(this, "setOptions", g);
                var l = g.plotOptions[this.type],
                p = h.plotOptions || {};
                this.userOptions = g.userOptions;
                h = b(l, e.series, h.plotOptions && h.plotOptions[this.type], a);
                this.tooltipOptions = b(u.tooltip, u.plotOptions.series && u.plotOptions.series.tooltip, u.plotOptions[this.type].tooltip, c.tooltip.userOptions, e.series && e.series.tooltip, e[this.type].tooltip, a.tooltip);
                this.stickyTracking = v(a.stickyTracking, p[this.type] && p[this.type].stickyTracking, p.series && p.series.stickyTracking, this.tooltipOptions.shared && !this.noSharedTooltip ? !0 : h.stickyTracking);
                null === l.marker && delete h.marker;
                this.zoneAxis = h.zoneAxis;
                c = this.zones = (h.zones || []).slice(); ! h.negativeColor && !h.negativeFillColor || h.zones || (e = {
                    value: h[this.zoneAxis + "Threshold"] || h.threshold || 0,
                    className: "highcharts-negative"
                },
                d || (e.color = h.negativeColor, e.fillColor = h.negativeFillColor), c.push(e));
                c.length && z(c[c.length - 1].value) && c.push(d ? {}: {
                    color: this.color,
                    fillColor: this.fillColor
                });
                k(this, "afterSetOptions", {
                    options: h
                });
                return h
            },
            getName: function() {
                return v(this.options.name, "Series " + (this.index + 1))
            },
            getCyclic: function(a, b, c) {
                var d = this.chart,
                e = this.userOptions,
                h = a + "Index",
                g = a + "Counter",
                l = c ? c.length: v(d.options.chart[a + "Count"], d[a + "Count"]);
                if (!b) {
                    var p = v(e[h], e["_" + h]);
                    z(p) || (d.series.length || (d[g] = 0), e["_" + h] = p = d[g] % l, d[g] += 1);
                    c && (b = c[p])
                }
                "undefined" !== typeof p && (this[h] = p);
                this[a] = b
            },
            getColor: function() {
                this.chart.styledMode ? this.getCyclic("color") : this.options.colorByPoint ? this.options.color = null: this.getCyclic("color", this.options.color || H[this.type].color, this.chart.options.colors)
            },
            getPointsCollection: function() {
                return (this.hasGroupedData ? this.points: this.data) || []
            },
            getSymbol: function() {
                this.getCyclic("symbol", this.options.marker.symbol, this.chart.options.symbols)
            },
            findPointIndex: function(a, b) {
                var d = a.id,
                c = a.x,
                e = this.points,
                h, l = this.options.dataSorting;
                if (d) var p = this.chart.get(d);
                else if (this.linkedParent || this.enabledDataSorting) {
                    var k = l && l.matchByName ? "name": "index";
                    p = n(e,
                    function(d) {
                        return ! d.touched && d[k] === a[k]
                    });
                    if (!p) return
                }
                if (p) {
                    var u = p && p.index;
                    "undefined" !== typeof u && (h = !0)
                }
                "undefined" === typeof u && g(c) && (u = this.xData.indexOf(c, b)); - 1 !== u && "undefined" !== typeof u && this.cropped && (u = u >= this.cropStart ? u - this.cropStart: u); ! h && e[u] && e[u].touched && (u = void 0);
                return u
            },
            drawLegendSymbol: m.drawLineMarker,
            updateData: function(a, b) {
                var d = this.options,
                c = d.dataSorting,
                e = this.points,
                h = [],
                l,
                p,
                k,
                u = this.requireSorting,
                f = a.length === e.length,
                n = !0;
                this.xIncrement = null;
                a.forEach(function(a, b) {
                    var p = z(a) && this.pointClass.prototype.optionsToObject.call({
                        series: this
                    },
                    a) || {};
                    var n = p.x;
                    if (p.id || g(n)) {
                        if (n = this.findPointIndex(p, k), -1 === n || "undefined" === typeof n ? h.push(a) : e[n] && a !== d.data[n] ? (e[n].update(a, !1, null, !1), e[n].touched = !0, u && (k = n + 1)) : e[n] && (e[n].touched = !0), !f || b !== n || c && c.enabled || this.hasDerivedData) l = !0
                    } else h.push(a)
                },
                this);
                if (l) for (a = e.length; a--;)(p = e[a]) && !p.touched && p.remove && p.remove(!1, b);
                else ! f || c && c.enabled ? n = !1 : (a.forEach(function(a, d) {
                    e[d].update && a !== e[d].y && e[d].update(a, !1, null, !1)
                }), h.length = 0);
                e.forEach(function(a) {
                    a && (a.touched = !1)
                });
                if (!n) return ! 1;
                h.forEach(function(a) {
                    this.addPoint(a, !1, null, null, !1)
                },
                this);
                null === this.xIncrement && this.xData && this.xData.length && (this.xIncrement = F(this.xData), this.autoIncrement());
                return ! 0
            },
            setData: function(a, b, c, h) {
                var d = this,
                l = d.points,
                k = l && l.length || 0,
                u, f = d.options,
                n = d.chart,
                I = f.dataSorting,
                w = null,
                q = d.xAxis;
                w = f.turboThreshold;
                var H = this.xData,
                m = this.yData,
                G = (u = d.pointArrayMap) && u.length,
                t = f.keys,
                x = 0,
                D = 1,
                z;
                a = a || [];
                u = a.length;
                b = v(b, !0);
                I && I.enabled && (a = this.sortData(a)); ! 1 !== h && u && k && !d.cropped && !d.hasGroupedData && d.visible && !d.isSeriesBoosting && (z = this.updateData(a, c));
                if (!z) {
                    d.xIncrement = null;
                    d.colorCounter = 0;
                    this.parallelArrays.forEach(function(a) {
                        d[a + "Data"].length = 0
                    });
                    if (w && u > w) if (w = d.getFirstValidPoint(a), g(w)) for (c = 0; c < u; c++) H[c] = this.autoIncrement(),
                    m[c] = a[c];
                    else if (e(w)) if (G) for (c = 0; c < u; c++) h = a[c],
                    H[c] = h[0],
                    m[c] = h.slice(1, G + 1);
                    else for (t && (x = t.indexOf("x"), D = t.indexOf("y"), x = 0 <= x ? x: 0, D = 0 <= D ? D: 1), c = 0; c < u; c++) h = a[c],
                    H[c] = h[x],
                    m[c] = h[D];
                    else A(12, !1, n);
                    else for (c = 0; c < u; c++)"undefined" !== typeof a[c] && (h = {
                        series: d
                    },
                    d.pointClass.prototype.applyOptions.apply(h, [a[c]]), d.updateParallelArrays(h, c));
                    m && p(m[0]) && A(14, !0, n);
                    d.data = [];
                    d.options.data = d.userOptions.data = a;
                    for (c = k; c--;) l[c] && l[c].destroy && l[c].destroy();
                    q && (q.minRange = q.userMinRange);
                    d.isDirty = n.isDirtyBox = !0;
                    d.isDirtyData = !!l;
                    c = !1
                }
                "point" === f.legendType && (this.processData(), this.generatePoints());
                b && n.redraw(c)
            },
            sortData: function(a) {
                var d = this,
                b = d.options.dataSorting.sortKey || "y",
                c = function(a, d) {
                    return z(d) && a.pointClass.prototype.optionsToObject.call({
                        series: a
                    },
                    d) || {}
                };
                a.forEach(function(b, e) {
                    a[e] = c(d, b);
                    a[e].index = e
                },
                this);
                a.concat().sort(function(a, d) {
                    a = q(b, a);
                    d = q(b, d);
                    return d < a ? -1 : d > a ? 1 : 0
                }).forEach(function(a, d) {
                    a.x = d
                },
                this);
                d.linkedSeries && d.linkedSeries.forEach(function(d) {
                    var b = d.options,
                    e = b.data;
                    b.dataSorting && b.dataSorting.enabled || !e || (e.forEach(function(b, h) {
                        e[h] = c(d, b);
                        a[h] && (e[h].x = a[h].x, e[h].index = h)
                    }), d.setData(e, !1))
                });
                return a
            },
            processData: function(a) {
                var d = this.xData,
                b = this.yData,
                c = d.length;
                var e = 0;
                var h = this.xAxis,
                g = this.options;
                var l = g.cropThreshold;
                var p = this.getExtremesFromAll || g.getExtremesFromAll,
                k = this.isCartesian;
                g = h && h.val2lin;
                var u = h && h.isLog,
                f = this.requireSorting;
                if (k && !this.isDirty && !h.isDirty && !this.yAxis.isDirty && !a) return ! 1;
                if (h) {
                    a = h.getExtremes();
                    var n = a.min;
                    var v = a.max
                }
                if (k && this.sorted && !p && (!l || c > l || this.forceCrop)) if (d[c - 1] < n || d[0] > v) d = [],
                b = [];
                else if (this.yData && (d[0] < n || d[c - 1] > v)) {
                    e = this.cropData(this.xData, this.yData, n, v);
                    d = e.xData;
                    b = e.yData;
                    e = e.start;
                    var w = !0
                }
                for (l = d.length || 1; --l;) if (c = u ? g(d[l]) - g(d[l - 1]) : d[l] - d[l - 1], 0 < c && ("undefined" === typeof q || c < q)) var q = c;
                else 0 > c && f && (A(15, !1, this.chart), f = !1);
                this.cropped = w;
                this.cropStart = e;
                this.processedXData = d;
                this.processedYData = b;
                this.closestPointRange = this.basePointRange = q
            },
            cropData: function(a, b, c, e, h) {
                var d = a.length,
                g = 0,
                l = d,
                p;
                h = v(h, this.cropShoulder);
                for (p = 0; p < d; p++) if (a[p] >= c) {
                    g = Math.max(0, p - h);
                    break
                }
                for (c = p; c < d; c++) if (a[c] > e) {
                    l = c + h;
                    break
                }
                return {
                    xData: a.slice(g, l),
                    yData: b.slice(g, l),
                    start: g,
                    end: l
                }
            },
            generatePoints: function() {
                var a = this.options,
                b = a.data,
                c = this.data,
                e, h = this.processedXData,
                g = this.processedYData,
                p = this.pointClass,
                u = h.length,
                f = this.cropStart || 0,
                n = this.hasGroupedData;
                a = a.keys;
                var v = [],
                w;
                c || n || (c = [], c.length = b.length, c = this.data = c);
                a && n && (this.options.keys = !1);
                for (w = 0; w < u; w++) {
                    var q = f + w;
                    if (n) {
                        var H = (new p).init(this, [h[w]].concat(l(g[w])));
                        H.dataGroup = this.groupMap[w];
                        H.dataGroup.options && (H.options = H.dataGroup.options, t(H, H.dataGroup.options), delete H.dataLabels)
                    } else(H = c[q]) || "undefined" === typeof b[q] || (c[q] = H = (new p).init(this, b[q], h[w]));
                    H && (H.index = q, v[w] = H)
                }
                this.options.keys = a;
                if (c && (u !== (e = c.length) || n)) for (w = 0; w < e; w++) w !== f || n || (w += u),
                c[w] && (c[w].destroyElements(), c[w].plotX = void 0);
                this.data = c;
                this.points = v;
                k(this, "afterGeneratePoints")
            },
            getXExtremes: function(a) {
                return {
                    min: C(a),
                    max: F(a)
                }
            },
            getExtremes: function(a) {
                var d = this.xAxis,
                b = this.yAxis,
                c = this.processedXData || this.xData,
                h = [],
                l = 0,
                p = 0;
                var u = 0;
                var f = this.requireSorting ? this.cropShoulder: 0,
                n = b ? b.positiveValuesOnly: !1,
                v;
                a = a || this.stackedYData || this.processedYData || [];
                b = a.length;
                d && (u = d.getExtremes(), p = u.min, u = u.max);
                for (v = 0; v < b; v++) {
                    var w = c[v];
                    var q = a[v];
                    var H = (g(q) || e(q)) && (q.length || 0 < q || !n);
                    w = this.getExtremesFromAll || this.options.getExtremesFromAll || this.cropped || !d || (c[v + f] || w) >= p && (c[v - f] || w) <= u;
                    if (H && w) if (H = q.length) for (; H--;) g(q[H]) && (h[l++] = q[H]);
                    else h[l++] = q
                }
                this.dataMin = C(h);
                this.dataMax = F(h);
                k(this, "afterGetExtremes")
            },
            getFirstValidPoint: function(a) {
                for (var d = null,
                b = a.length,
                c = 0; null === d && c < b;) d = a[c],
                c++;
                return d
            },
            translate: function() {
                this.processedXData || this.processData();
                this.generatePoints();
                var a = this.options,
                b = a.stacking,
                c = this.xAxis,
                h = c.categories,
                l = this.enabledDataSorting,
                p = this.yAxis,
                u = this.points,
                f = u.length,
                n = !!this.modifyValue,
                w, q = this.pointPlacementToXValue(),
                H = !!q,
                m = a.threshold,
                G = a.startFromThreshold ? m: 0,
                t,
                x = this.zoneAxis || "y",
                D = Number.MAX_VALUE;
                for (w = 0; w < f; w++) {
                    var M = u[w],
                    Q = M.x,
                    A = M.y,
                    r = M.low,
                    C = b && p.stacks[(this.negStacks && A < (G ? 0 : m) ? "-": "") + this.stackKey];
                    p.positiveValuesOnly && null !== A && 0 >= A && (M.isNull = !0);
                    M.plotX = t = L(B(c.translate(Q, 0, 0, 0, 1, q, "flags" === this.type), -1E5, 1E5));
                    if (b && this.visible && C && C[Q]) {
                        var F = this.getStackIndicator(F, Q, this.index);
                        if (!M.isNull) {
                            var J = C[Q];
                            var E = J.points[F.key]
                        }
                    }
                    e(E) && (r = E[0], A = E[1], r === G && F.key === C[Q].base && (r = v(g(m) && m, p.min)), p.positiveValuesOnly && 0 >= r && (r = null), M.total = M.stackTotal = J.total, M.percentage = J.total && M.y / J.total * 100, M.stackY = A, this.irregularWidths || J.setOffset(this.pointXOffset || 0, this.barW || 0));
                    M.yBottom = z(r) ? B(p.translate(r, 0, 1, 0, 1), -1E5, 1E5) : null;
                    n && (A = this.modifyValue(A, M));
                    M.plotY = "number" === typeof A && Infinity !== A ? B(p.translate(A, 0, 1, 0, 1), -1E5, 1E5) : void 0;
                    M.isInside = this.isPointInside(M);
                    M.clientX = H ? L(c.translate(Q, 0, 0, 0, 1, q)) : t;
                    M.negative = M[x] < (a[x + "Threshold"] || m || 0);
                    M.category = h && "undefined" !== typeof h[M.x] ? h[M.x] : M.x;
                    if (!M.isNull && !1 !== M.visible) {
                        "undefined" !== typeof y && (D = Math.min(D, Math.abs(t - y)));
                        var y = t
                    }
                    M.zone = this.zones.length && M.getZone(); ! M.graphic && this.group && l && (M.isNew = !0)
                }
                this.closestPointRangePx = D;
                k(this, "afterTranslate")
            },
            getValidPoints: function(a, b, c) {
                var d = this.chart;
                return (a || this.points || []).filter(function(a) {
                    return b && !d.isInsidePlot(a.plotX, a.plotY, d.inverted) ? !1 : !1 !== a.visible && (c || !a.isNull)
                })
            },
            getClipBox: function(a, b) {
                var d = this.options,
                c = this.chart,
                e = c.inverted,
                h = this.xAxis,
                g = h && this.yAxis;
                a && !1 === d.clip && g ? a = e ? {
                    y: -c.chartWidth + g.len + g.pos,
                    height: c.chartWidth,
                    width: c.chartHeight,
                    x: -c.chartHeight + h.len + h.pos
                }: {
                    y: -g.pos,
                    height: c.chartHeight,
                    width: c.chartWidth,
                    x: -h.pos
                }: (a = this.clipBox || c.clipBox, b && (a.width = c.plotSizeX, a.x = 0));
                return b ? {
                    width: a.width,
                    x: a.x
                }: a
            },
            setClip: function(a) {
                var d = this.chart,
                b = this.options,
                c = d.renderer,
                e = d.inverted,
                h = this.clipBox,
                g = this.getClipBox(a),
                l = this.sharedClipKey || ["_sharedClip", a && a.duration, a && a.easing, g.height, b.xAxis, b.yAxis].join(),
                p = d[l],
                k = d[l + "m"];
                a && (g.width = 0, e && (g.x = d.plotHeight + (!1 !== b.clip ? 0 : d.plotTop)));
                p ? d.hasLoaded || p.attr(g) : (a && (d[l + "m"] = k = c.clipRect(e ? d.plotSizeX + 99 : -99, e ? -d.plotLeft: -d.plotTop, 99, e ? d.chartWidth: d.chartHeight)), d[l] = p = c.clipRect(g), p.count = {
                    length: 0
                });
                a && !p.count[this.index] && (p.count[this.index] = !0, p.count.length += 1);
                if (!1 !== b.clip || a) this.group.clip(a || h ? p: d.clipRect),
                this.markerGroup.clip(k),
                this.sharedClipKey = l;
                a || (p.count[this.index] && (delete p.count[this.index], --p.count.length), 0 === p.count.length && l && d[l] && (h || (d[l] = d[l].destroy()), d[l + "m"] && (d[l + "m"] = d[l + "m"].destroy())))
            },
            animate: function(a) {
                var d = this.chart,
                b = y(this.options.animation);
                if (!d.hasRendered) if (a) this.setClip(b);
                else {
                    var c = this.sharedClipKey;
                    a = d[c];
                    var e = this.getClipBox(b, !0);
                    a && a.animate(e, b);
                    d[c + "m"] && d[c + "m"].animate({
                        width: e.width + 99,
                        x: e.x - (d.inverted ? 0 : 99)
                    },
                    b)
                }
            },
            afterAnimate: function() {
                this.setClip();
                k(this, "afterAnimate");
                this.finishedAnimating = !0
            },
            drawPoints: function() {
                var a = this.points,
                b = this.chart,
                c, e, h = this.options.marker,
                g = this[this.specialGroup] || this.markerGroup,
                l = this.xAxis,
                p = v(h.enabled, !l || l.isRadial ? !0 : null, this.closestPointRangePx >= h.enabledThreshold * h.radius);
                if (!1 !== h.enabled || this._hasPointMarkers) for (c = 0; c < a.length; c++) {
                    var k = a[c];
                    var u = (e = k.graphic) ? "animate": "attr";
                    var f = k.marker || {};
                    var n = !!k.marker;
                    if ((p && "undefined" === typeof f.enabled || f.enabled) && !k.isNull && !1 !== k.visible) {
                        var w = v(f.symbol, this.symbol);
                        var q = this.markerAttribs(k, k.selected && "select");
                        this.enabledDataSorting && (k.startXPos = l.reversed ? -q.width: l.width);
                        var H = !1 !== k.isInside;
                        e ? e[H ? "show": "hide"](H).animate(q) : H && (0 < q.width || k.hasImage) && (k.graphic = e = b.renderer.symbol(w, q.x, q.y, q.width, q.height, n ? f: h).add(g), this.enabledDataSorting && b.hasRendered && (e.attr({
                            x: k.startXPos
                        }), u = "animate"));
                        e && "animate" === u && e[H ? "show": "hide"](H).animate(q);
                        if (e && !b.styledMode) e[u](this.pointAttribs(k, k.selected && "select"));
                        e && e.addClass(k.getClassName(), !0)
                    } else e && (k.graphic = e.destroy())
                }
            },
            markerAttribs: function(a, b) {
                var d = this.options.marker,
                c = a.marker || {},
                e = c.symbol || d.symbol,
                h = v(c.radius, d.radius);
                b && (d = d.states[b], b = c.states && c.states[b], h = v(b && b.radius, d && d.radius, h + (d && d.radiusPlus || 0)));
                a.hasImage = e && 0 === e.indexOf("url");
                a.hasImage && (h = 0);
                a = {
                    x: Math.floor(a.plotX) - h,
                    y: a.plotY - h
                };
                h && (a.width = a.height = 2 * h);
                return a
            },
            pointAttribs: function(a, b) {
                var d = this.options.marker,
                c = a && a.options,
                e = c && c.marker || {},
                h = this.color,
                g = c && c.color,
                l = a && a.color;
                c = v(e.lineWidth, d.lineWidth);
                var p = a && a.zone && a.zone.color;
                a = 1;
                h = g || p || l || h;
                g = e.fillColor || d.fillColor || h;
                h = e.lineColor || d.lineColor || h;
                b = b || "normal";
                d = d.states[b];
                b = e.states && e.states[b] || {};
                c = v(b.lineWidth, d.lineWidth, c + v(b.lineWidthPlus, d.lineWidthPlus, 0));
                g = b.fillColor || d.fillColor || g;
                h = b.lineColor || d.lineColor || h;
                a = v(b.opacity, d.opacity, a);
                return {
                    stroke: h,
                    "stroke-width": c,
                    fill: g,
                    opacity: a
                }
            },
            destroy: function(d) {
                var b = this,
                c = b.chart,
                e = /AppleWebKit\/533/.test(G.navigator.userAgent),
                h,
                g,
                l = b.data || [],
                p,
                u;
                k(b, "destroy");
                this.removeEvents(d); (b.axisTypes || []).forEach(function(a) { (u = b[a]) && u.series && (x(u.series, b), u.isDirty = u.forceRedraw = !0)
                });
                b.legendItem && b.chart.legend.destroyItem(b);
                for (g = l.length; g--;)(p = l[g]) && p.destroy && p.destroy();
                b.points = null;
                r.clearTimeout(b.animationTimeout);
                a(b,
                function(a, d) {
                    a instanceof Q && !a.survive && (h = e && "group" === d ? "hide": "destroy", a[h]())
                });
                c.hoverSeries === b && (c.hoverSeries = null);
                x(c.series, b);
                c.orderSeries();
                a(b,
                function(a, c) {
                    d && "hcEvents" === c || delete b[c]
                })
            },
            getGraphPath: function(a, b, c) {
                var d = this,
                e = d.options,
                h = e.step,
                g, l = [],
                p = [],
                k;
                a = a || d.points; (g = a.reversed) && a.reverse(); (h = {
                    right: 1,
                    center: 2
                } [h] || h && 3) && g && (h = 4 - h);
                a = this.getValidPoints(a, !1, !(e.connectNulls && !b && !c));
                a.forEach(function(g, u) {
                    var f = g.plotX,
                    n = g.plotY,
                    v = a[u - 1]; (g.leftCliff || v && v.rightCliff) && !c && (k = !0);
                    g.isNull && !z(b) && 0 < u ? k = !e.connectNulls: g.isNull && !b ? k = !0 : (0 === u || k ? u = ["M", g.plotX, g.plotY] : d.getPointSpline ? u = d.getPointSpline(a, g, u) : h ? (u = 1 === h ? ["L", v.plotX, n] : 2 === h ? ["L", (v.plotX + f) / 2, v.plotY, "L", (v.plotX + f) / 2, n] : ["L", f, v.plotY], u.push("L", f, n)) : u = ["L", f, n], p.push(g.x), h && (p.push(g.x), 2 === h && p.push(g.x)), l.push.apply(l, u), k = !1)
                });
                l.xMap = p;
                return d.graphPath = l
            },
            drawGraph: function() {
                var a = this,
                b = this.options,
                c = (this.gappedPath || this.getGraphPath).call(this),
                e = this.chart.styledMode,
                h = [["graph", "highcharts-graph"]];
                e || h[0].push(b.lineColor || this.color || "#cccccc", b.dashStyle);
                h = a.getZonesGraphs(h);
                h.forEach(function(d, h) {
                    var g = d[0],
                    l = a[g],
                    p = l ? "animate": "attr";
                    l ? (l.endX = a.preventGraphAnimation ? null: c.xMap, l.animate({
                        d: c
                    })) : c.length && (a[g] = l = a.chart.renderer.path(c).addClass(d[1]).attr({
                        zIndex: 1
                    }).add(a.group));
                    l && !e && (g = {
                        stroke: d[2],
                        "stroke-width": b.lineWidth,
                        fill: a.fillGraph && a.color || "none"
                    },
                    d[3] ? g.dashstyle = d[3] : "square" !== b.linecap && (g["stroke-linecap"] = g["stroke-linejoin"] = "round"), l[p](g).shadow(2 > h && b.shadow));
                    l && (l.startX = c.xMap, l.isArea = c.isArea)
                })
            },
            getZonesGraphs: function(a) {
                this.zones.forEach(function(d, b) {
                    b = ["zone-graph-" + b, "highcharts-graph highcharts-zone-graph-" + b + " " + (d.className || "")];
                    this.chart.styledMode || b.push(d.color || this.color, d.dashStyle || this.options.dashStyle);
                    a.push(b)
                },
                this);
                return a
            },
            applyZones: function() {
                var a = this,
                b = this.chart,
                c = b.renderer,
                e = this.zones,
                h, g, l = this.clips || [],
                p,
                k = this.graph,
                u = this.area,
                f = Math.max(b.chartWidth, b.chartHeight),
                n = this[(this.zoneAxis || "y") + "Axis"],
                w = b.inverted,
                q,
                H,
                m,
                t = !1;
                if (e.length && (k || u) && n && "undefined" !== typeof n.min) {
                    var G = n.reversed;
                    var x = n.horiz;
                    k && !this.showLine && k.hide();
                    u && u.hide();
                    var M = n.getExtremes();
                    e.forEach(function(d, e) {
                        h = G ? x ? b.plotWidth: 0 : x ? 0 : n.toPixels(M.min) || 0;
                        h = B(v(g, h), 0, f);
                        g = B(Math.round(n.toPixels(v(d.value, M.max), !0) || 0), 0, f);
                        t && (h = g = n.toPixels(M.max));
                        q = Math.abs(h - g);
                        H = Math.min(h, g);
                        m = Math.max(h, g);
                        n.isXAxis ? (p = {
                            x: w ? m: H,
                            y: 0,
                            width: q,
                            height: f
                        },
                        x || (p.x = b.plotHeight - p.x)) : (p = {
                            x: 0,
                            y: w ? m: H,
                            width: f,
                            height: q
                        },
                        x && (p.y = b.plotWidth - p.y));
                        w && c.isVML && (p = n.isXAxis ? {
                            x: 0,
                            y: G ? H: m,
                            height: p.width,
                            width: b.chartWidth
                        }: {
                            x: p.y - b.plotLeft - b.spacingBox.x,
                            y: 0,
                            width: p.height,
                            height: b.chartHeight
                        });
                        l[e] ? l[e].animate(p) : l[e] = c.clipRect(p);
                        k && a["zone-graph-" + e].clip(l[e]);
                        u && a["zone-area-" + e].clip(l[e]);
                        t = d.value > M.max;
                        a.resetZones && 0 === g && (g = void 0)
                    });
                    this.clips = l
                } else a.visible && (k && k.show(!0), u && u.show(!0))
            },
            invertGroups: function(a) {
                function d() { ["group", "markerGroup"].forEach(function(d) {
                        b[d] && (c.renderer.isVML && b[d].attr({
                            width: b.yAxis.len,
                            height: b.xAxis.len
                        }), b[d].width = b.yAxis.len, b[d].height = b.xAxis.len, b[d].invert(b.isRadialSeries ? !1 : a))
                    })
                }
                var b = this,
                c = b.chart;
                b.xAxis && (b.eventsToUnbind.push(E(c, "resize", d)), d(), b.invertGroups = d)
            },
            plotGroup: function(a, b, c, e, h) {
                var d = this[a],
                g = !d;
                g && (this[a] = d = this.chart.renderer.g().attr({
                    zIndex: e || .1
                }).add(h));
                d.addClass("highcharts-" + b + " highcharts-series-" + this.index + " highcharts-" + this.type + "-series " + (z(this.colorIndex) ? "highcharts-color-" + this.colorIndex + " ": "") + (this.options.className || "") + (d.hasClass("highcharts-tracker") ? " highcharts-tracker": ""), !0);
                d.attr({
                    visibility: c
                })[g ? "attr": "animate"](this.getPlotBox());
                return d
            },
            getPlotBox: function() {
                var a = this.chart,
                b = this.xAxis,
                c = this.yAxis;
                a.inverted && (b = c, c = this.xAxis);
                return {
                    translateX: b ? b.left: a.plotLeft,
                    translateY: c ? c.top: a.plotTop,
                    scaleX: 1,
                    scaleY: 1
                }
            },
            removeEvents: function(a) {
                a ? this.eventsToUnbind.length && (this.eventsToUnbind.forEach(function(a) {
                    a()
                }), this.eventsToUnbind.length = 0) : D(this)
            },
            render: function() {
                var a = this,
                b = a.chart,
                c = a.options,
                e = !a.finishedAnimating && b.renderer.isSVG && y(c.animation).duration,
                g = a.visible ? "inherit": "hidden",
                l = c.zIndex,
                p = a.hasRendered,
                u = b.seriesGroup,
                f = b.inverted;
                k(this, "render");
                var n = a.plotGroup("group", "series", g, l, u);
                a.markerGroup = a.plotGroup("markerGroup", "markers", g, l, u);
                e && a.animate && a.animate(!0);
                n.inverted = a.isCartesian || a.invertable ? f: !1;
                a.drawGraph && (a.drawGraph(), a.applyZones());
                a.visible && a.drawPoints();
                a.drawDataLabels && a.drawDataLabels();
                a.redrawPoints && a.redrawPoints();
                a.drawTracker && !1 !== a.options.enableMouseTracking && a.drawTracker();
                a.invertGroups(f); ! 1 === c.clip || a.sharedClipKey || p || n.clip(b.clipRect);
                e && a.animate && a.animate();
                p || (a.animationTimeout = h(function() {
                    a.afterAnimate()
                },
                e || 0));
                a.isDirty = !1;
                a.hasRendered = !0;
                k(a, "afterRender")
            },
            redraw: function() {
                var a = this.chart,
                b = this.isDirty || this.isDirtyData,
                c = this.group,
                e = this.xAxis,
                h = this.yAxis;
                c && (a.inverted && c.attr({
                    width: a.plotWidth,
                    height: a.plotHeight
                }), c.animate({
                    translateX: v(e && e.left, a.plotLeft),
                    translateY: v(h && h.top, a.plotTop)
                }));
                this.translate();
                this.render();
                b && delete this.kdTree
            },
            kdAxisArray: ["clientX", "plotY"],
            searchPoint: function(a, b) {
                var d = this.xAxis,
                c = this.yAxis,
                e = this.chart.inverted;
                return this.searchKDTree({
                    clientX: e ? d.len - a.chartY + d.pos: a.chartX - d.pos,
                    plotY: e ? c.len - a.chartX + c.pos: a.chartY - c.pos
                },
                b, a)
            },
            buildKDTree: function(a) {
                function d(a, c, e) {
                    var h;
                    if (h = a && a.length) {
                        var g = b.kdAxisArray[c % e];
                        a.sort(function(a, d) {
                            return a[g] - d[g]
                        });
                        h = Math.floor(h / 2);
                        return {
                            point: a[h],
                            left: d(a.slice(0, h), c + 1, e),
                            right: d(a.slice(h + 1), c + 1, e)
                        }
                    }
                }
                this.buildingKdTree = !0;
                var b = this,
                c = -1 < b.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                delete b.kdTree;
                h(function() {
                    b.kdTree = d(b.getValidPoints(null, !b.directTouch), c, c);
                    b.buildingKdTree = !1
                },
                b.options.kdNow || a && "touchstart" === a.type ? 0 : 1)
            },
            searchKDTree: function(a, b, c) {
                function d(a, b, c, p) {
                    var k = b.point,
                    u = e.kdAxisArray[c % p],
                    f = k;
                    var n = z(a[h]) && z(k[h]) ? Math.pow(a[h] - k[h], 2) : null;
                    var v = z(a[g]) && z(k[g]) ? Math.pow(a[g] - k[g], 2) : null;
                    v = (n || 0) + (v || 0);
                    k.dist = z(v) ? Math.sqrt(v) : Number.MAX_VALUE;
                    k.distX = z(n) ? Math.sqrt(n) : Number.MAX_VALUE;
                    u = a[u] - k[u];
                    v = 0 > u ? "left": "right";
                    n = 0 > u ? "right": "left";
                    b[v] && (v = d(a, b[v], c + 1, p), f = v[l] < f[l] ? v: k);
                    b[n] && Math.sqrt(u * u) < f[l] && (a = d(a, b[n], c + 1, p), f = a[l] < f[l] ? a: f);
                    return f
                }
                var e = this,
                h = this.kdAxisArray[0],
                g = this.kdAxisArray[1],
                l = b ? "distX": "dist";
                b = -1 < e.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                this.kdTree || this.buildingKdTree || this.buildKDTree(c);
                if (this.kdTree) return d(a, this.kdTree, b, b)
            },
            pointPlacementToXValue: function() {
                var a = this.options,
                b = a.pointRange,
                c = this.xAxis;
                a = a.pointPlacement;
                "between" === a && (a = c.reversed ? -.5 : .5);
                return g(a) ? a * v(b, c.pointRange) : 0
            },
            isPointInside: function(a) {
                return "undefined" !== typeof a.plotY && "undefined" !== typeof a.plotX && 0 <= a.plotY && a.plotY <= this.yAxis.len && 0 <= a.plotX && a.plotX <= this.xAxis.len
            }
        });
        ""
    });
    P(y, "parts/Stacking.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var J = m.correctFloat,
        r = m.defined,
        E = m.destroyObjectProperties,
        y = m.format,
        F = m.objectEach,
        C = m.pick;
        m = f.Axis;
        var B = f.Chart,
        L = f.Series;
        f.StackItem = function(f, m, A, t, n) {
            var k = f.chart.inverted;
            this.axis = f;
            this.isNegative = A;
            this.options = m = m || {};
            this.x = t;
            this.total = null;
            this.points = {};
            this.stack = n;
            this.rightCliff = this.leftCliff = 0;
            this.alignOptions = {
                align: m.align || (k ? A ? "left": "right": "center"),
                verticalAlign: m.verticalAlign || (k ? "middle": A ? "bottom": "top"),
                y: m.y,
                x: m.x
            };
            this.textAlign = m.textAlign || (k ? A ? "right": "left": "center")
        };
        f.StackItem.prototype = {
            destroy: function() {
                E(this, this.axis)
            },
            render: function(f) {
                var m = this.axis.chart,
                z = this.options,
                t = z.format;
                t = t ? y(t, this, m) : z.formatter.call(this);
                this.label ? this.label.attr({
                    text: t,
                    visibility: "hidden"
                }) : (this.label = m.renderer.label(t, null, null, z.shape, null, null, z.useHTML, !1, "stack-labels"), t = {
                    text: t,
                    rotation: z.rotation,
                    padding: C(z.padding, 5),
                    visibility: "hidden"
                },
                this.label.attr(t), m.styledMode || this.label.css(z.style), this.label.added || this.label.add(f));
                this.label.labelrank = m.plotHeight
            },
            setOffset: function(f, m, A, t, n) {
                var k = this.axis,
                q = k.chart;
                t = k.translate(k.usePercentage ? 100 : t ? t: this.total, 0, 0, 0, 1);
                A = k.translate(A ? A: 0);
                A = r(t) && Math.abs(t - A);
                f = C(n, q.xAxis[0].translate(this.x)) + f;
                k = r(t) && this.getStackBox(q, this, f, t, m, A, k);
                m = this.label;
                A = this.isNegative;
                f = "justify" === C(this.options.overflow, "justify");
                var e = this.textAlign;
                m && k && (n = m.getBBox(), t = m.padding, e = "left" === e ? q.inverted ? -t: t: "right" === e ? n.width: q.inverted && "center" === e ? n.width / 2 : q.inverted ? A ? n.width + t: -t: n.width / 2, A = q.inverted ? n.height / 2 : A ? -t: n.height, this.alignOptions.x = C(this.options.x, 0), this.alignOptions.y = C(this.options.y, 0), k.x -= e, k.y -= A, m.align(this.alignOptions, null, k), q.isInsidePlot(m.alignAttr.x + e - this.alignOptions.x, m.alignAttr.y + A - this.alignOptions.y) ? m.show() : (m.alignAttr.y = -9999, f = !1), f && L.prototype.justifyDataLabel.call(this.axis, m, this.alignOptions, m.alignAttr, n, k), m.attr({
                    x: m.alignAttr.x,
                    y: m.alignAttr.y
                }), C(!f && this.options.crop, !0) && ((q = q.isInsidePlot(m.x - t + m.width, m.y) && q.isInsidePlot(m.x + t, m.y)) || m.hide()))
            },
            getStackBox: function(f, m, A, t, n, k, q) {
                var e = m.axis.reversed,
                c = f.inverted;
                f = q.height + q.pos - (c ? f.plotLeft: f.plotTop);
                m = m.isNegative && !e || !m.isNegative && e;
                return {
                    x: c ? m ? t: t - k: A,
                    y: c ? f - A - n: m ? f - t - k: f - t,
                    width: c ? k: n,
                    height: c ? n: k
                }
            }
        };
        B.prototype.getStacks = function() {
            var f = this,
            m = f.inverted;
            f.yAxis.forEach(function(f) {
                f.stacks && f.hasVisibleSeries && (f.oldStacks = f.stacks)
            });
            f.series.forEach(function(x) {
                var t = x.xAxis && x.xAxis.options || {}; ! x.options.stacking || !0 !== x.visible && !1 !== f.options.chart.ignoreHiddenSeries || (x.stackKey = [x.type, C(x.options.stack, ""), m ? t.top: t.left, m ? t.height: t.width].join())
            })
        };
        m.prototype.buildStacks = function() {
            var m = this.series,
            x = C(this.options.reversedStacks, !0),
            A = m.length,
            t;
            if (!this.isXAxis) {
                this.usePercentage = !1;
                for (t = A; t--;) {
                    var n = m[x ? t: A - t - 1];
                    n.setStackedPoints()
                }
                for (t = 0; t < A; t++) m[t].modifyStacks();
                f.fireEvent(this, "afterBuildStacks")
            }
        };
        m.prototype.renderStackTotals = function() {
            var f = this.chart,
            m = f.renderer,
            A = this.stacks,
            t = this.stackTotalGroup;
            t || (this.stackTotalGroup = t = m.g("stack-labels").attr({
                visibility: "visible",
                zIndex: 6
            }).add());
            t.translate(f.plotLeft, f.plotTop);
            F(A,
            function(f) {
                F(f,
                function(k) {
                    k.render(t)
                })
            })
        };
        m.prototype.resetStacks = function() {
            var f = this,
            m = f.stacks;
            f.isXAxis || F(m,
            function(m) {
                F(m,
                function(t, n) {
                    t.touched < f.stacksTouched ? (t.destroy(), delete m[n]) : (t.total = null, t.cumulative = null)
                })
            })
        };
        m.prototype.cleanStacks = function() {
            if (!this.isXAxis) {
                if (this.oldStacks) var f = this.stacks = this.oldStacks;
                F(f,
                function(f) {
                    F(f,
                    function(f) {
                        f.cumulative = f.total
                    })
                })
            }
        };
        L.prototype.setStackedPoints = function() {
            if (this.options.stacking && (!0 === this.visible || !1 === this.chart.options.chart.ignoreHiddenSeries)) {
                var m = this.processedXData,
                x = this.processedYData,
                A = [],
                t = x.length,
                n = this.options,
                k = n.threshold,
                q = C(n.startFromThreshold && k, 0),
                e = n.stack;
                n = n.stacking;
                var c = this.stackKey,
                g = "-" + c,
                p = this.negStacks,
                b = this.yAxis,
                a = b.stacks,
                v = b.oldStacks,
                D, w;
                b.stacksTouched += 1;
                for (w = 0; w < t; w++) {
                    var l = m[w];
                    var h = x[w];
                    var u = this.getStackIndicator(u, l, this.index);
                    var H = u.key;
                    var M = (D = p && h < (q ? 0 : k)) ? g: c;
                    a[M] || (a[M] = {});
                    a[M][l] || (v[M] && v[M][l] ? (a[M][l] = v[M][l], a[M][l].total = null) : a[M][l] = new f.StackItem(b, b.options.stackLabels, D, l, e));
                    M = a[M][l];
                    null !== h ? (M.points[H] = M.points[this.index] = [C(M.cumulative, q)], r(M.cumulative) || (M.base = H), M.touched = b.stacksTouched, 0 < u.index && !1 === this.singleStacks && (M.points[H][0] = M.points[this.index + "," + l + ",0"][0])) : M.points[H] = M.points[this.index] = null;
                    "percent" === n ? (D = D ? c: g, p && a[D] && a[D][l] ? (D = a[D][l], M.total = D.total = Math.max(D.total, M.total) + Math.abs(h) || 0) : M.total = J(M.total + (Math.abs(h) || 0))) : M.total = J(M.total + (h || 0));
                    M.cumulative = C(M.cumulative, q) + (h || 0);
                    null !== h && (M.points[H].push(M.cumulative), A[w] = M.cumulative)
                }
                "percent" === n && (b.usePercentage = !0);
                this.stackedYData = A;
                b.oldStacks = {}
            }
        };
        L.prototype.modifyStacks = function() {
            var f = this,
            m = f.stackKey,
            A = f.yAxis.stacks,
            t = f.processedXData,
            n, k = f.options.stacking;
            f[k + "Stacker"] && [m, "-" + m].forEach(function(q) {
                for (var e = t.length,
                c, g; e--;) if (c = t[e], n = f.getStackIndicator(n, c, f.index, q), g = (c = A[q] && A[q][c]) && c.points[n.key]) f[k + "Stacker"](g, c, e)
            })
        };
        L.prototype.percentStacker = function(f, m, A) {
            m = m.total ? 100 / m.total: 0;
            f[0] = J(f[0] * m);
            f[1] = J(f[1] * m);
            this.stackedYData[A] = f[1]
        };
        L.prototype.getStackIndicator = function(f, m, A, t) { ! r(f) || f.x !== m || t && f.key !== t ? f = {
                x: m,
                index: 0,
                key: t
            }: f.index++;
            f.key = [A, m, f.index].join();
            return f
        }
    });
    P(y, "parts/Dynamics.js", [y["parts/Globals.js"], y["parts/Point.js"], y["parts/Time.js"], y["parts/Utilities.js"]],
    function(f, m, J, r) {
        var E = r.addEvent,
        y = r.animate,
        F = r.createElement,
        C = r.css,
        B = r.defined,
        L = r.erase,
        z = r.error,
        x = r.extend,
        A = r.fireEvent,
        t = r.isArray,
        n = r.isNumber,
        k = r.isObject,
        q = r.isString,
        e = r.merge,
        c = r.objectEach,
        g = r.pick,
        p = r.relativeLength,
        b = r.setAnimation,
        a = r.splat,
        v = f.Axis;
        r = f.Chart;
        var D = f.Series,
        w = f.seriesTypes;
        f.cleanRecursively = function(a, b) {
            var e = {};
            c(a,
            function(c, h) {
                if (k(a[h], !0) && !a.nodeType && b[h]) c = f.cleanRecursively(a[h], b[h]),
                Object.keys(c).length && (e[h] = c);
                else if (k(a[h]) || a[h] !== b[h]) e[h] = a[h]
            });
            return e
        };
        x(r.prototype, {
            addSeries: function(a, b, c) {
                var e, h = this;
                a && (b = g(b, !0), A(h, "addSeries", {
                    options: a
                },
                function() {
                    e = h.initSeries(a);
                    h.isDirtyLegend = !0;
                    h.linkSeries();
                    e.enabledDataSorting && e.setData(a.data, !1);
                    A(h, "afterAddSeries", {
                        series: e
                    });
                    b && h.redraw(c)
                }));
                return e
            },
            addAxis: function(a, b, c, e) {
                return this.createAxis(b ? "xAxis": "yAxis", {
                    axis: a,
                    redraw: c,
                    animation: e
                })
            },
            addColorAxis: function(a, b, c) {
                return this.createAxis("colorAxis", {
                    axis: a,
                    redraw: b,
                    animation: c
                })
            },
            createAxis: function(b, c) {
                var h = this.options,
                l = "colorAxis" === b,
                p = c.redraw,
                k = c.animation;
                c = e(c.axis, {
                    index: this[b].length,
                    isX: "xAxis" === b
                });
                var n = l ? new f.ColorAxis(this, c) : new v(this, c);
                h[b] = a(h[b] || {});
                h[b].push(c);
                l && (this.isDirtyLegend = !0, this.axes.forEach(function(a) {
                    a.series = []
                }), this.series.forEach(function(a) {
                    a.bindAxes();
                    a.isDirtyData = !0
                }));
                g(p, !0) && this.redraw(k);
                return n
            },
            showLoading: function(a) {
                var b = this,
                c = b.options,
                e = b.loadingDiv,
                l = c.loading,
                p = function() {
                    e && C(e, {
                        left: b.plotLeft + "px",
                        top: b.plotTop + "px",
                        width: b.plotWidth + "px",
                        height: b.plotHeight + "px"
                    })
                };
                e || (b.loadingDiv = e = F("div", {
                    className: "highcharts-loading highcharts-loading-hidden"
                },
                null, b.container), b.loadingSpan = F("span", {
                    className: "highcharts-loading-inner"
                },
                null, e), E(b, "redraw", p));
                e.className = "highcharts-loading";
                b.loadingSpan.innerHTML = g(a, c.lang.loading, "");
                b.styledMode || (C(e, x(l.style, {
                    zIndex: 10
                })), C(b.loadingSpan, l.labelStyle), b.loadingShown || (C(e, {
                    opacity: 0,
                    display: ""
                }), y(e, {
                    opacity: l.style.opacity || .5
                },
                {
                    duration: l.showDuration || 0
                })));
                b.loadingShown = !0;
                p()
            },
            hideLoading: function() {
                var a = this.options,
                b = this.loadingDiv;
                b && (b.className = "highcharts-loading highcharts-loading-hidden", this.styledMode || y(b, {
                    opacity: 0
                },
                {
                    duration: a.loading.hideDuration || 100,
                    complete: function() {
                        C(b, {
                            display: "none"
                        })
                    }
                }));
                this.loadingShown = !1
            },
            propsRequireDirtyBox: "backgroundColor borderColor borderWidth borderRadius plotBackgroundColor plotBackgroundImage plotBorderColor plotBorderWidth plotShadow shadow".split(" "),
            propsRequireReflow: "margin marginTop marginRight marginBottom marginLeft spacing spacingTop spacingRight spacingBottom spacingLeft".split(" "),
            propsRequireUpdateSeries: "chart.inverted chart.polar chart.ignoreHiddenSeries chart.type colors plotOptions time tooltip".split(" "),
            collectionsWithUpdate: ["xAxis", "yAxis", "zAxis", "series"],
            update: function(b, h, k, v) {
                var l = this,
                u = {
                    credits: "addCredits",
                    title: "setTitle",
                    subtitle: "setSubtitle",
                    caption: "setCaption"
                },
                w,
                d,
                m,
                H = b.isResponsiveOptions,
                t = [];
                A(l, "update", {
                    options: b
                });
                H || l.setResponsive(!1, !0);
                b = f.cleanRecursively(b, l.options);
                e(!0, l.userOptions, b);
                if (w = b.chart) {
                    e(!0, l.options.chart, w);
                    "className" in w && l.setClassName(w.className);
                    "reflow" in w && l.setReflow(w.reflow);
                    if ("inverted" in w || "polar" in w || "type" in w) {
                        l.propFromSeries();
                        var D = !0
                    }
                    "alignTicks" in w && (D = !0);
                    c(w,
                    function(a, b) { - 1 !== l.propsRequireUpdateSeries.indexOf("chart." + b) && (d = !0); - 1 !== l.propsRequireDirtyBox.indexOf(b) && (l.isDirtyBox = !0);
                        H || -1 === l.propsRequireReflow.indexOf(b) || (m = !0)
                    }); ! l.styledMode && "style" in w && l.renderer.setStyle(w.style)
                } ! l.styledMode && b.colors && (this.options.colors = b.colors);
                b.plotOptions && e(!0, this.options.plotOptions, b.plotOptions);
                b.time && this.time === f.time && (this.time = new J(b.time));
                c(b,
                function(a, b) {
                    if (l[b] && "function" === typeof l[b].update) l[b].update(a, !1);
                    else if ("function" === typeof l[u[b]]) l[u[b]](a);
                    "chart" !== b && -1 !== l.propsRequireUpdateSeries.indexOf(b) && (d = !0)
                });
                this.collectionsWithUpdate.forEach(function(d) {
                    if (b[d]) {
                        if ("series" === d) {
                            var c = [];
                            l[d].forEach(function(a, d) {
                                a.options.isInternal || c.push(g(a.options.index, d))
                            })
                        }
                        a(b[d]).forEach(function(a, b) { (b = B(a.id) && l.get(a.id) || l[d][c ? c[b] : b]) && b.coll === d && (b.update(a, !1), k && (b.touched = !0)); ! b && k && l.collectionsWithInit[d] && (l.collectionsWithInit[d][0].apply(l, [a].concat(l.collectionsWithInit[d][1] || []).concat([!1])).touched = !0)
                        });
                        k && l[d].forEach(function(a) {
                            a.touched || a.options.isInternal ? delete a.touched: t.push(a)
                        })
                    }
                });
                t.forEach(function(a) {
                    a.remove && a.remove(!1)
                });
                D && l.axes.forEach(function(a) {
                    a.update({},
                    !1)
                });
                d && l.getSeriesOrderByLinks().forEach(function(a) {
                    a.chart && a.update({},
                    !1)
                },
                this);
                b.loading && e(!0, l.options.loading, b.loading);
                D = w && w.width;
                w = w && w.height;
                q(w) && (w = p(w, D || l.chartWidth));
                m || n(D) && D !== l.chartWidth || n(w) && w !== l.chartHeight ? l.setSize(D, w, v) : g(h, !0) && l.redraw(v);
                A(l, "afterUpdate", {
                    options: b,
                    redraw: h,
                    animation: v
                })
            },
            setSubtitle: function(a, b) {
                this.applyDescription("subtitle", a);
                this.layOutTitles(b)
            },
            setCaption: function(a, b) {
                this.applyDescription("caption", a);
                this.layOutTitles(b)
            }
        });
        r.prototype.collectionsWithInit = {
            xAxis: [r.prototype.addAxis, [!0]],
            yAxis: [r.prototype.addAxis, [!1]],
            series: [r.prototype.addSeries]
        };
        x(m.prototype, {
            update: function(a, b, c, e) {
                function h() {
                    l.applyOptions(a);
                    var e = d && l.hasDummyGraphic;
                    e = null === l.y ? !e: e;
                    d && e && (l.graphic = d.destroy(), delete l.hasDummyGraphic);
                    k(a, !0) && (d && d.element && a && a.marker && "undefined" !== typeof a.marker.symbol && (l.graphic = d.destroy()), a && a.dataLabels && l.dataLabel && (l.dataLabel = l.dataLabel.destroy()), l.connector && (l.connector = l.connector.destroy()));
                    u = l.index;
                    p.updateParallelArrays(l, u);
                    n.data[u] = k(n.data[u], !0) || k(a, !0) ? l.options: g(a, n.data[u]);
                    p.isDirty = p.isDirtyData = !0; ! p.fixedBox && p.hasCartesianSeries && (f.isDirtyBox = !0);
                    "point" === n.legendType && (f.isDirtyLegend = !0);
                    b && f.redraw(c)
                }
                var l = this,
                p = l.series,
                d = l.graphic,
                u, f = p.chart,
                n = p.options;
                b = g(b, !0); ! 1 === e ? h() : l.firePointEvent("update", {
                    options: a
                },
                h)
            },
            remove: function(a, b) {
                this.series.removePoint(this.series.data.indexOf(this), a, b)
            }
        });
        x(D.prototype, {
            addPoint: function(a, b, c, e, p) {
                var h = this.options,
                l = this.data,
                d = this.chart,
                k = this.xAxis;
                k = k && k.hasNames && k.names;
                var u = h.data,
                f = this.xData,
                n;
                b = g(b, !0);
                var w = {
                    series: this
                };
                this.pointClass.prototype.applyOptions.apply(w, [a]);
                var v = w.x;
                var q = f.length;
                if (this.requireSorting && v < f[q - 1]) for (n = !0; q && f[q - 1] > v;) q--;
                this.updateParallelArrays(w, "splice", q, 0, 0);
                this.updateParallelArrays(w, q);
                k && w.name && (k[v] = w.name);
                u.splice(q, 0, a);
                n && (this.data.splice(q, 0, null), this.processData());
                "point" === h.legendType && this.generatePoints();
                c && (l[0] && l[0].remove ? l[0].remove(!1) : (l.shift(), this.updateParallelArrays(w, "shift"), u.shift())); ! 1 !== p && A(this, "addPoint", {
                    point: w
                });
                this.isDirtyData = this.isDirty = !0;
                b && d.redraw(e)
            },
            removePoint: function(a, c, e) {
                var h = this,
                l = h.data,
                p = l[a],
                k = h.points,
                d = h.chart,
                u = function() {
                    k && k.length === l.length && k.splice(a, 1);
                    l.splice(a, 1);
                    h.options.data.splice(a, 1);
                    h.updateParallelArrays(p || {
                        series: h
                    },
                    "splice", a, 1);
                    p && p.destroy();
                    h.isDirty = !0;
                    h.isDirtyData = !0;
                    c && d.redraw()
                };
                b(e, d);
                c = g(c, !0);
                p ? p.firePointEvent("remove", null, u) : u()
            },
            remove: function(a, b, c, e) {
                function h() {
                    l.destroy(e);
                    l.remove = null;
                    p.isDirtyLegend = p.isDirtyBox = !0;
                    p.linkSeries();
                    g(a, !0) && p.redraw(b)
                }
                var l = this,
                p = l.chart; ! 1 !== c ? A(l, "remove", null, h) : h()
            },
            update: function(a, b) {
                a = f.cleanRecursively(a, this.userOptions);
                A(this, "update", {
                    options: a
                });
                var c = this,
                h = c.chart,
                l = c.userOptions,
                p = c.initialType || c.type,
                k = a.type || l.type || h.options.chart.type,
                d = !(this.hasDerivedData || a.dataGrouping || k && k !== this.type || "undefined" !== typeof a.pointStart || a.pointInterval || a.pointIntervalUnit || a.keys),
                n = w[p].prototype,
                v,
                q = ["group", "markerGroup", "dataLabelsGroup", "transformGroup"],
                m = ["eventOptions", "navigatorSeries", "baseSeries"],
                t = c.finishedAnimating && {
                    animation: !1
                },
                D = {};
                d && (m.push("data", "isDirtyData", "points", "processedXData", "processedYData", "xIncrement", "_hasPointMarkers", "_hasPointLabels", "mapMap", "mapData", "minY", "maxY", "minX", "maxX"), !1 !== a.visible && m.push("area", "graph"), c.parallelArrays.forEach(function(a) {
                    m.push(a + "Data")
                }), a.data && (a.dataSorting && x(c.options.dataSorting, a.dataSorting), this.setData(a.data, !1)));
                a = e(l, t, {
                    index: "undefined" === typeof l.index ? c.index: l.index,
                    pointStart: g(l.pointStart, c.xData[0])
                },
                !d && {
                    data: c.options.data
                },
                a);
                d && a.data && (a.data = c.options.data);
                m = q.concat(m);
                m.forEach(function(a) {
                    m[a] = c[a];
                    delete c[a]
                });
                c.remove(!1, null, !1, !0);
                for (v in n) c[v] = void 0;
                w[k || p] ? x(c, w[k || p].prototype) : z(17, !0, h, {
                    missingModuleFor: k || p
                });
                m.forEach(function(a) {
                    c[a] = m[a]
                });
                c.init(h, a);
                if (d && this.points) {
                    var r = c.options; ! 1 === r.visible ? (D.graphic = 1, D.dataLabel = 1) : c._hasPointLabels || (k = r.marker, n = r.dataLabels, k && (!1 === k.enabled || "symbol" in k) && (D.graphic = 1), n && !1 === n.enabled && (D.dataLabel = 1));
                    this.points.forEach(function(a) {
                        a && a.series && (a.resolveColor(), Object.keys(D).length && a.destroyElements(D), !1 === r.showInLegend && a.legendItem && h.legend.destroyItem(a))
                    },
                    this)
                }
                a.zIndex !== l.zIndex && q.forEach(function(d) {
                    c[d] && c[d].attr({
                        zIndex: a.zIndex
                    })
                });
                c.initialType = p;
                h.linkSeries();
                A(this, "afterUpdate");
                g(b, !0) && h.redraw(d ? void 0 : !1)
            },
            setName: function(a) {
                this.name = this.options.name = this.userOptions.name = a;
                this.chart.isDirtyLegend = !0
            }
        });
        x(v.prototype, {
            update: function(a, b) {
                var h = this.chart,
                l = a && a.events || {};
                a = e(this.userOptions, a);
                h.options[this.coll].indexOf && (h.options[this.coll][h.options[this.coll].indexOf(this.userOptions)] = a);
                c(h.options[this.coll].events,
                function(a, b) {
                    "undefined" === typeof l[b] && (l[b] = void 0)
                });
                this.destroy(!0);
                this.init(h, x(a, {
                    events: l
                }));
                h.isDirtyBox = !0;
                g(b, !0) && h.redraw()
            },
            remove: function(a) {
                for (var b = this.chart,
                c = this.coll,
                e = this.series,
                l = e.length; l--;) e[l] && e[l].remove(!1);
                L(b.axes, this);
                L(b[c], this);
                t(b.options[c]) ? b.options[c].splice(this.options.index, 1) : delete b.options[c];
                b[c].forEach(function(a, b) {
                    a.options.index = a.userOptions.index = b
                });
                this.destroy();
                b.isDirtyBox = !0;
                g(a, !0) && b.redraw()
            },
            setTitle: function(a, b) {
                this.update({
                    title: a
                },
                b)
            },
            setCategories: function(a, b) {
                this.update({
                    categories: a
                },
                b)
            }
        })
    });
    P(y, "parts/AreaSeries.js", [y["parts/Globals.js"], y["parts/Color.js"], y["mixins/legend-symbol.js"], y["parts/Utilities.js"]],
    function(f, m, J, r) {
        var E = m.parse,
        y = r.objectEach,
        F = r.pick;
        m = r.seriesType;
        var C = f.Series;
        m("area", "line", {
            softThreshold: !1,
            threshold: 0
        },
        {
            singleStacks: !1,
            getStackPoints: function(f) {
                var m = [],
                z = [],
                x = this.xAxis,
                A = this.yAxis,
                t = A.stacks[this.stackKey],
                n = {},
                k = this.index,
                q = A.series,
                e = q.length,
                c = F(A.options.reversedStacks, !0) ? 1 : -1,
                g;
                f = f || this.points;
                if (this.options.stacking) {
                    for (g = 0; g < f.length; g++) f[g].leftNull = f[g].rightNull = void 0,
                    n[f[g].x] = f[g];
                    y(t,
                    function(b, a) {
                        null !== b.total && z.push(a)
                    });
                    z.sort(function(b, a) {
                        return b - a
                    });
                    var p = q.map(function(b) {
                        return b.visible
                    });
                    z.forEach(function(b, a) {
                        var f = 0,
                        q, w;
                        if (n[b] && !n[b].isNull) m.push(n[b]),
                        [ - 1, 1].forEach(function(l) {
                            var h = 1 === l ? "rightNull": "leftNull",
                            f = 0,
                            v = t[z[a + l]];
                            if (v) for (g = k; 0 <= g && g < e;) q = v.points[g],
                            q || (g === k ? n[b][h] = !0 : p[g] && (w = t[b].points[g]) && (f -= w[1] - w[0])),
                            g += c;
                            n[b][1 === l ? "rightCliff": "leftCliff"] = f
                        });
                        else {
                            for (g = k; 0 <= g && g < e;) {
                                if (q = t[b].points[g]) {
                                    f = q[1];
                                    break
                                }
                                g += c
                            }
                            f = A.translate(f, 0, 1, 0, 1);
                            m.push({
                                isNull: !0,
                                plotX: x.translate(b, 0, 0, 0, 1),
                                x: b,
                                plotY: f,
                                yBottom: f
                            })
                        }
                    })
                }
                return m
            },
            getGraphPath: function(f) {
                var m = C.prototype.getGraphPath,
                z = this.options,
                x = z.stacking,
                A = this.yAxis,
                t, n = [],
                k = [],
                q = this.index,
                e = A.stacks[this.stackKey],
                c = z.threshold,
                g = Math.round(A.getThreshold(z.threshold));
                z = F(z.connectNulls, "percent" === x);
                var p = function(b, p, l) {
                    var h = f[b];
                    b = x && e[h.x].points[q];
                    var u = h[l + "Null"] || 0;
                    l = h[l + "Cliff"] || 0;
                    h = !0;
                    if (l || u) {
                        var w = (u ? b[0] : b[1]) + l;
                        var v = b[0] + l;
                        h = !!u
                    } else ! x && f[p] && f[p].isNull && (w = v = c);
                    "undefined" !== typeof w && (k.push({
                        plotX: a,
                        plotY: null === w ? g: A.getThreshold(w),
                        isNull: h,
                        isCliff: !0
                    }), n.push({
                        plotX: a,
                        plotY: null === v ? g: A.getThreshold(v),
                        doCurve: !1
                    }))
                };
                f = f || this.points;
                x && (f = this.getStackPoints(f));
                for (t = 0; t < f.length; t++) {
                    x || (f[t].leftCliff = f[t].rightCliff = f[t].leftNull = f[t].rightNull = void 0);
                    var b = f[t].isNull;
                    var a = F(f[t].rectPlotX, f[t].plotX);
                    var v = F(f[t].yBottom, g);
                    if (!b || z) z || p(t, t - 1, "left"),
                    b && !x && z || (k.push(f[t]), n.push({
                        x: t,
                        plotX: a,
                        plotY: v
                    })),
                    z || p(t, t + 1, "right")
                }
                t = m.call(this, k, !0, !0);
                n.reversed = !0;
                b = m.call(this, n, !0, !0);
                b.length && (b[0] = "L");
                b = t.concat(b);
                m = m.call(this, k, !1, z);
                b.xMap = t.xMap;
                this.areaPath = b;
                return m
            },
            drawGraph: function() {
                this.areaPath = [];
                C.prototype.drawGraph.apply(this);
                var f = this,
                m = this.areaPath,
                z = this.options,
                x = [["area", "highcharts-area", this.color, z.fillColor]];
                this.zones.forEach(function(m, t) {
                    x.push(["zone-area-" + t, "highcharts-area highcharts-zone-area-" + t + " " + m.className, m.color || f.color, m.fillColor || z.fillColor])
                });
                x.forEach(function(x) {
                    var t = x[0],
                    n = f[t],
                    k = n ? "animate": "attr",
                    q = {};
                    n ? (n.endX = f.preventGraphAnimation ? null: m.xMap, n.animate({
                        d: m
                    })) : (q.zIndex = 0, n = f[t] = f.chart.renderer.path(m).addClass(x[1]).add(f.group), n.isArea = !0);
                    f.chart.styledMode || (q.fill = F(x[3], E(x[2]).setOpacity(F(z.fillOpacity, .75)).get()));
                    n[k](q);
                    n.startX = m.xMap;
                    n.shiftUnit = z.step ? 2 : 1
                })
            },
            drawLegendSymbol: J.drawRectangle
        });
        ""
    });
    P(y, "parts/SplineSeries.js", [y["parts/Utilities.js"]],
    function(f) {
        var m = f.pick;
        f = f.seriesType;
        f("spline", "line", {},
        {
            getPointSpline: function(f, r, E) {
                var y = r.plotX,
                F = r.plotY,
                C = f[E - 1];
                E = f[E + 1];
                if (C && !C.isNull && !1 !== C.doCurve && !r.isCliff && E && !E.isNull && !1 !== E.doCurve && !r.isCliff) {
                    f = C.plotY;
                    var B = E.plotX;
                    E = E.plotY;
                    var J = 0;
                    var z = (1.5 * y + C.plotX) / 2.5;
                    var x = (1.5 * F + f) / 2.5;
                    B = (1.5 * y + B) / 2.5;
                    var A = (1.5 * F + E) / 2.5;
                    B !== z && (J = (A - x) * (B - y) / (B - z) + F - A);
                    x += J;
                    A += J;
                    x > f && x > F ? (x = Math.max(f, F), A = 2 * F - x) : x < f && x < F && (x = Math.min(f, F), A = 2 * F - x);
                    A > E && A > F ? (A = Math.max(E, F), x = 2 * F - A) : A < E && A < F && (A = Math.min(E, F), x = 2 * F - A);
                    r.rightContX = B;
                    r.rightContY = A
                }
                r = ["C", m(C.rightContX, C.plotX), m(C.rightContY, C.plotY), m(z, y), m(x, F), y, F];
                C.rightContX = C.rightContY = null;
                return r
            }
        });
        ""
    });
    P(y, "parts/AreaSplineSeries.js", [y["parts/Globals.js"], y["mixins/legend-symbol.js"], y["parts/Utilities.js"]],
    function(f, m, y) {
        y = y.seriesType;
        var r = f.seriesTypes.area.prototype;
        y("areaspline", "spline", f.defaultPlotOptions.area, {
            getStackPoints: r.getStackPoints,
            getGraphPath: r.getGraphPath,
            drawGraph: r.drawGraph,
            drawLegendSymbol: m.drawRectangle
        });
        ""
    });
    P(y, "parts/ColumnSeries.js", [y["parts/Globals.js"], y["parts/Color.js"], y["mixins/legend-symbol.js"], y["parts/Utilities.js"]],
    function(f, m, y, r) {
        "";
        var E = m.parse,
        J = r.animObject,
        F = r.clamp,
        C = r.defined,
        B = r.extend,
        L = r.isNumber,
        z = r.merge,
        x = r.pick;
        m = r.seriesType;
        var A = f.Series;
        m("column", "line", {
            borderRadius: 0,
            crisp: !0,
            groupPadding: .2,
            marker: null,
            pointPadding: .1,
            minPointLength: 0,
            cropThreshold: 50,
            pointRange: null,
            states: {
                hover: {
                    halo: !1,
                    brightness: .1
                },
                select: {
                    color: "#cccccc",
                    borderColor: "#000000"
                }
            },
            dataLabels: {
                align: null,
                verticalAlign: null,
                y: null
            },
            softThreshold: !1,
            startFromThreshold: !0,
            stickyTracking: !1,
            tooltip: {
                distance: 6
            },
            threshold: 0,
            borderColor: "#ffffff"
        },
        {
            cropShoulder: 0,
            directTouch: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            negStacks: !0,
            init: function() {
                A.prototype.init.apply(this, arguments);
                var f = this,
                n = f.chart;
                n.hasRendered && n.series.forEach(function(k) {
                    k.type === f.type && (k.isDirty = !0)
                })
            },
            getColumnMetrics: function() {
                var f = this,
                n = f.options,
                k = f.xAxis,
                q = f.yAxis,
                e = k.options.reversedStacks;
                e = k.reversed && !e || !k.reversed && e;
                var c, g = {},
                p = 0; ! 1 === n.grouping ? p = 1 : f.chart.series.forEach(function(a) {
                    var b = a.yAxis,
                    e = a.options;
                    if (a.type === f.type && (a.visible || !f.chart.options.chart.ignoreHiddenSeries) && q.len === b.len && q.pos === b.pos) {
                        if (e.stacking) {
                            c = a.stackKey;
                            "undefined" === typeof g[c] && (g[c] = p++);
                            var h = g[c]
                        } else ! 1 !== e.grouping && (h = p++);
                        a.columnIndex = h
                    }
                });
                var b = Math.min(Math.abs(k.transA) * (k.ordinalSlope || n.pointRange || k.closestPointRange || k.tickInterval || 1), k.len),
                a = b * n.groupPadding,
                v = (b - 2 * a) / (p || 1);
                n = Math.min(n.maxPointWidth || k.len, x(n.pointWidth, v * (1 - 2 * n.pointPadding)));
                f.columnMetrics = {
                    width: n,
                    offset: (v - n) / 2 + (a + ((f.columnIndex || 0) + (e ? 1 : 0)) * v - b / 2) * (e ? -1 : 1)
                };
                return f.columnMetrics
            },
            crispCol: function(f, n, k, q) {
                var e = this.chart,
                c = this.borderWidth,
                g = -(c % 2 ? .5 : 0);
                c = c % 2 ? .5 : 1;
                e.inverted && e.renderer.isVML && (c += 1);
                this.options.crisp && (k = Math.round(f + k) + g, f = Math.round(f) + g, k -= f);
                q = Math.round(n + q) + c;
                g = .5 >= Math.abs(n) && .5 < q;
                n = Math.round(n) + c;
                q -= n;
                g && q && (--n, q += 1);
                return {
                    x: f,
                    y: n,
                    width: k,
                    height: q
                }
            },
            translate: function() {
                var f = this,
                n = f.chart,
                k = f.options,
                q = f.dense = 2 > f.closestPointRange * f.xAxis.transA;
                q = f.borderWidth = x(k.borderWidth, q ? 0 : 1);
                var e = f.xAxis,
                c = f.yAxis,
                g = k.threshold,
                p = f.translatedThreshold = c.getThreshold(g),
                b = x(k.minPointLength, 5),
                a = f.getColumnMetrics(),
                v = a.width,
                m = f.barW = Math.max(v, 1 + 2 * q),
                w = f.pointXOffset = a.offset,
                l = f.dataMin,
                h = f.dataMax;
                n.inverted && (p -= .5);
                k.pointPadding && (m = Math.ceil(m));
                A.prototype.translate.apply(f);
                f.points.forEach(function(a) {
                    var k = x(a.yBottom, p),
                    u = 999 + Math.abs(k),
                    q = v,
                    D = a.plotX;
                    u = F(a.plotY, -u, c.len + u);
                    var d = a.plotX + w,
                    t = m,
                    z = Math.min(u, k),
                    r = Math.max(u, k) - z;
                    if (b && Math.abs(r) < b) {
                        r = b;
                        var A = !c.reversed && !a.negative || c.reversed && a.negative;
                        a.y === g && f.dataMax <= g && c.min < g && l !== h && (A = !A);
                        z = Math.abs(z - p) > b ? k - b: p - (A ? b: 0)
                    }
                    C(a.options.pointWidth) && (q = t = Math.ceil(a.options.pointWidth), d -= Math.round((q - v) / 2));
                    a.barX = d;
                    a.pointWidth = q;
                    a.tooltipPos = n.inverted ? [c.len + c.pos - n.plotLeft - u, e.len + e.pos - n.plotTop - (D || 0) - w - t / 2, r] : [d + t / 2, u + c.pos - n.plotTop, r];
                    a.shapeType = f.pointClass.prototype.shapeType || "rect";
                    a.shapeArgs = f.crispCol.apply(f, a.isNull ? [d, p, t, 0] : [d, z, t, r])
                })
            },
            getSymbol: f.noop,
            drawLegendSymbol: y.drawRectangle,
            drawGraph: function() {
                this.group[this.dense ? "addClass": "removeClass"]("highcharts-dense-data")
            },
            pointAttribs: function(f, n) {
                var k = this.options,
                q = this.pointAttrToOptions || {};
                var e = q.stroke || "borderColor";
                var c = q["stroke-width"] || "borderWidth",
                g = f && f.color || this.color,
                p = f && f[e] || k[e] || this.color || g,
                b = f && f[c] || k[c] || this[c] || 0;
                q = f && f.options.dashStyle || k.dashStyle;
                var a = x(f && f.opacity, k.opacity, 1);
                if (f && this.zones.length) {
                    var v = f.getZone();
                    g = f.options.color || v && (v.color || f.nonZonedColor) || this.color;
                    v && (p = v.borderColor || p, q = v.dashStyle || q, b = v.borderWidth || b)
                }
                n && f && (f = z(k.states[n], f.options.states && f.options.states[n] || {}), n = f.brightness, g = f.color || "undefined" !== typeof n && E(g).brighten(f.brightness).get() || g, p = f[e] || p, b = f[c] || b, q = f.dashStyle || q, a = x(f.opacity, a));
                e = {
                    fill: g,
                    stroke: p,
                    "stroke-width": b,
                    opacity: a
                };
                q && (e.dashstyle = q);
                return e
            },
            drawPoints: function() {
                var f = this,
                n = this.chart,
                k = f.options,
                q = n.renderer,
                e = k.animationLimit || 250,
                c;
                f.points.forEach(function(g) {
                    var p = g.graphic,
                    b = !!p,
                    a = p && n.pointCount < e ? "animate": "attr";
                    if (L(g.plotY) && null !== g.y) {
                        c = g.shapeArgs;
                        p && g.hasNewShapeType() && (p = p.destroy());
                        f.enabledDataSorting && (g.startXPos = f.xAxis.reversed ? -(c ? c.width: 0) : f.xAxis.width);
                        p || (g.graphic = p = q[g.shapeType](c).add(g.group || f.group)) && f.enabledDataSorting && n.hasRendered && n.pointCount < e && (p.attr({
                            x: g.startXPos
                        }), b = !0, a = "animate");
                        if (p && b) p[a](z(c));
                        if (k.borderRadius) p[a]({
                            r: k.borderRadius
                        });
                        n.styledMode || p[a](f.pointAttribs(g, g.selected && "select")).shadow(!1 !== g.allowShadow && k.shadow, null, k.stacking && !k.borderRadius);
                        p.addClass(g.getClassName(), !0)
                    } else p && (g.graphic = p.destroy())
                })
            },
            animate: function(f) {
                var n = this,
                k = this.yAxis,
                q = n.options,
                e = this.chart.inverted,
                c = {},
                g = e ? "translateX": "translateY";
                if (f) c.scaleY = .001,
                f = F(k.toPixels(q.threshold), k.pos, k.pos + k.len),
                e ? c.translateX = f - k.len: c.translateY = f,
                n.clipBox && n.setClip(),
                n.group.attr(c);
                else {
                    var p = n.group.attr(g);
                    n.group.animate({
                        scaleY: 1
                    },
                    B(J(n.options.animation), {
                        step: function(b, a) {
                            n.group && (c[g] = p + a.pos * (k.pos - p), n.group.attr(c))
                        }
                    }))
                }
            },
            remove: function() {
                var f = this,
                n = f.chart;
                n.hasRendered && n.series.forEach(function(k) {
                    k.type === f.type && (k.isDirty = !0)
                });
                A.prototype.remove.apply(f, arguments)
            }
        });
        ""
    });
    P(y, "parts/BarSeries.js", [y["parts/Utilities.js"]],
    function(f) {
        f = f.seriesType;
        f("bar", "column", null, {
            inverted: !0
        });
        ""
    });
    P(y, "parts/ScatterSeries.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.addEvent;
        m = m.seriesType;
        var r = f.Series;
        m("scatter", "line", {
            lineWidth: 0,
            findNearestPointBy: "xy",
            jitter: {
                x: 0,
                y: 0
            },
            marker: {
                enabled: !0
            },
            tooltip: {
                headerFormat: '<span style="color:{point.color}">\u25cf</span> <span style="font-size: 10px"> {series.name}</span><br/>',
                pointFormat: "x: <b>{point.x}</b><br/>y: <b>{point.y}</b><br/>"
            }
        },
        {
            sorted: !1,
            requireSorting: !1,
            noSharedTooltip: !0,
            trackerGroups: ["group", "markerGroup", "dataLabelsGroup"],
            takeOrdinalPosition: !1,
            drawGraph: function() {
                this.options.lineWidth && r.prototype.drawGraph.call(this)
            },
            applyJitter: function() {
                var f = this,
                m = this.options.jitter,
                r = this.points.length;
                m && this.points.forEach(function(C, B) { ["x", "y"].forEach(function(y, z) {
                        var x = "plot" + y.toUpperCase();
                        if (m[y] && !C.isNull) {
                            var A = f[y + "Axis"];
                            var t = m[y] * A.transA;
                            if (A && !A.isLog) {
                                var n = Math.max(0, C[x] - t);
                                A = Math.min(A.len, C[x] + t);
                                z = 1E4 * Math.sin(B + z * r);
                                C[x] = n + (A - n) * (z - Math.floor(z));
                                "x" === y && (C.clientX = C.plotX)
                            }
                        }
                    })
                })
            }
        });
        y(r, "afterTranslate",
        function() {
            this.applyJitter && this.applyJitter()
        });
        ""
    });
    P(y, "mixins/centered-series.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.isNumber,
        r = m.pick,
        E = m.relativeLength,
        N = f.deg2rad;
        f.CenteredSeriesMixin = {
            getCenter: function() {
                var f = this.options,
                m = this.chart,
                B = 2 * (f.slicedOffset || 0),
                y = m.plotWidth - 2 * B,
                z = m.plotHeight - 2 * B,
                x = f.center,
                A = Math.min(y, z),
                t = f.size,
                n = f.innerSize || 0;
                "string" === typeof t && (t = parseFloat(t));
                "string" === typeof n && (n = parseFloat(n));
                f = [r(x[0], "50%"), r(x[1], "50%"), r(t && 0 > t ? void 0 : f.size, "100%"), r(n && 0 > n ? void 0 : f.innerSize || 0, "0%")];
                m.angular && (f[3] = 0);
                for (x = 0; 4 > x; ++x) t = f[x],
                m = 2 > x || 2 === x && /%$/.test(t),
                f[x] = E(t, [y, z, A, f[2]][x]) + (m ? B: 0);
                f[3] > f[2] && (f[3] = f[2]);
                return f
            },
            getStartAndEndRadians: function(f, m) {
                f = y(f) ? f: 0;
                m = y(m) && m > f && 360 > m - f ? m: f + 360;
                return {
                    start: N * (f + -90),
                    end: N * (m + -90)
                }
            }
        }
    });
    P(y, "parts/PieSeries.js", [y["parts/Globals.js"], y["mixins/legend-symbol.js"], y["parts/Point.js"], y["parts/Utilities.js"]],
    function(f, m, y, r) {
        var E = r.addEvent,
        J = r.clamp,
        F = r.defined,
        C = r.fireEvent,
        B = r.isNumber,
        L = r.merge,
        z = r.pick,
        x = r.relativeLength,
        A = r.seriesType,
        t = r.setAnimation;
        r = f.CenteredSeriesMixin;
        var n = r.getStartAndEndRadians,
        k = f.noop,
        q = f.Series;
        A("pie", "line", {
            center: [null, null],
            clip: !1,
            colorByPoint: !0,
            dataLabels: {
                allowOverlap: !0,
                connectorPadding: 5,
                connectorShape: "fixedOffset",
                crookDistance: "70%",
                distance: 30,
                enabled: !0,
                formatter: function() {
                    return this.point.isNull ? void 0 : this.point.name
                },
                softConnector: !0,
                x: 0
            },
            fillColor: void 0,
            ignoreHiddenPoint: !0,
            inactiveOtherPoints: !0,
            legendType: "point",
            marker: null,
            size: null,
            showInLegend: !1,
            slicedOffset: 10,
            stickyTracking: !1,
            tooltip: {
                followPointer: !0
            },
            borderColor: "#ffffff",
            borderWidth: 1,
            lineWidth: void 0,
            states: {
                hover: {
                    brightness: .1
                }
            }
        },
        {
            isCartesian: !1,
            requireSorting: !1,
            directTouch: !0,
            noSharedTooltip: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            axisTypes: [],
            pointAttribs: f.seriesTypes.column.prototype.pointAttribs,
            animate: function(e) {
                var c = this,
                g = c.points,
                f = c.startAngleRad;
                e || g.forEach(function(b) {
                    var a = b.graphic,
                    e = b.shapeArgs;
                    a && e && (a.attr({
                        r: z(b.startR, c.center && c.center[3] / 2),
                        start: f,
                        end: f
                    }), a.animate({
                        r: e.r,
                        start: e.start,
                        end: e.end
                    },
                    c.options.animation))
                })
            },
            hasData: function() {
                return !! this.processedXData.length
            },
            updateTotals: function() {
                var e, c = 0,
                g = this.points,
                f = g.length,
                b = this.options.ignoreHiddenPoint;
                for (e = 0; e < f; e++) {
                    var a = g[e];
                    c += b && !a.visible ? 0 : a.isNull ? 0 : a.y
                }
                this.total = c;
                for (e = 0; e < f; e++) a = g[e],
                a.percentage = 0 < c && (a.visible || !b) ? a.y / c * 100 : 0,
                a.total = c
            },
            generatePoints: function() {
                q.prototype.generatePoints.call(this);
                this.updateTotals()
            },
            getX: function(e, c, g) {
                var f = this.center,
                b = this.radii ? this.radii[g.index] : f[2] / 2;
                e = Math.asin(J((e - f[1]) / (b + g.labelDistance), -1, 1));
                return f[0] + (c ? -1 : 1) * Math.cos(e) * (b + g.labelDistance) + (0 < g.labelDistance ? (c ? -1 : 1) * this.options.dataLabels.padding: 0)
            },
            translate: function(e) {
                this.generatePoints();
                var c = 0,
                g = this.options,
                f = g.slicedOffset,
                b = f + (g.borderWidth || 0),
                a = n(g.startAngle, g.endAngle),
                k = this.startAngleRad = a.start;
                a = (this.endAngleRad = a.end) - k;
                var m = this.points,
                w = g.dataLabels.distance;
                g = g.ignoreHiddenPoint;
                var l, h = m.length;
                e || (this.center = e = this.getCenter());
                for (l = 0; l < h; l++) {
                    var u = m[l];
                    var q = k + c * a;
                    if (!g || u.visible) c += u.percentage / 100;
                    var t = k + c * a;
                    u.shapeType = "arc";
                    u.shapeArgs = {
                        x: e[0],
                        y: e[1],
                        r: e[2] / 2,
                        innerR: e[3] / 2,
                        start: Math.round(1E3 * q) / 1E3,
                        end: Math.round(1E3 * t) / 1E3
                    };
                    u.labelDistance = z(u.options.dataLabels && u.options.dataLabels.distance, w);
                    u.labelDistance = x(u.labelDistance, u.shapeArgs.r);
                    this.maxLabelDistance = Math.max(this.maxLabelDistance || 0, u.labelDistance);
                    t = (t + q) / 2;
                    t > 1.5 * Math.PI ? t -= 2 * Math.PI: t < -Math.PI / 2 && (t += 2 * Math.PI);
                    u.slicedTranslation = {
                        translateX: Math.round(Math.cos(t) * f),
                        translateY: Math.round(Math.sin(t) * f)
                    };
                    var r = Math.cos(t) * e[2] / 2;
                    var G = Math.sin(t) * e[2] / 2;
                    u.tooltipPos = [e[0] + .7 * r, e[1] + .7 * G];
                    u.half = t < -Math.PI / 2 || t > Math.PI / 2 ? 1 : 0;
                    u.angle = t;
                    q = Math.min(b, u.labelDistance / 5);
                    u.labelPosition = {
                        natural: {
                            x: e[0] + r + Math.cos(t) * u.labelDistance,
                            y: e[1] + G + Math.sin(t) * u.labelDistance
                        },
                        "final": {},
                        alignment: 0 > u.labelDistance ? "center": u.half ? "right": "left",
                        connectorPosition: {
                            breakAt: {
                                x: e[0] + r + Math.cos(t) * q,
                                y: e[1] + G + Math.sin(t) * q
                            },
                            touchingSliceAt: {
                                x: e[0] + r,
                                y: e[1] + G
                            }
                        }
                    }
                }
                C(this, "afterTranslate")
            },
            drawEmpty: function() {
                var e = this.options;
                if (0 === this.total) {
                    var c = this.center[0];
                    var g = this.center[1];
                    this.graph || (this.graph = this.chart.renderer.circle(c, g, 0).addClass("highcharts-graph").add(this.group));
                    this.graph.animate({
                        "stroke-width": e.borderWidth,
                        cx: c,
                        cy: g,
                        r: this.center[2] / 2,
                        fill: e.fillColor || "none",
                        stroke: e.color || "#cccccc"
                    },
                    this.options.animation)
                } else this.graph && (this.graph = this.graph.destroy())
            },
            redrawPoints: function() {
                var e = this,
                c = e.chart,
                g = c.renderer,
                f, b, a, k, n = e.options.shadow;
                this.drawEmpty(); ! n || e.shadowGroup || c.styledMode || (e.shadowGroup = g.g("shadow").attr({
                    zIndex: -1
                }).add(e.group));
                e.points.forEach(function(p) {
                    var l = {};
                    b = p.graphic;
                    if (!p.isNull && b) {
                        k = p.shapeArgs;
                        f = p.getTranslate();
                        if (!c.styledMode) {
                            var h = p.shadowGroup;
                            n && !h && (h = p.shadowGroup = g.g("shadow").add(e.shadowGroup));
                            h && h.attr(f);
                            a = e.pointAttribs(p, p.selected && "select")
                        }
                        p.delayedRendering ? (b.setRadialReference(e.center).attr(k).attr(f), c.styledMode || b.attr(a).attr({
                            "stroke-linejoin": "round"
                        }).shadow(n, h), p.delayedRendering = !1) : (b.setRadialReference(e.center), c.styledMode || L(!0, l, a), L(!0, l, k, f), b.animate(l));
                        b.attr({
                            visibility: p.visible ? "inherit": "hidden"
                        });
                        b.addClass(p.getClassName())
                    } else b && (p.graphic = b.destroy())
                })
            },
            drawPoints: function() {
                var e = this.chart.renderer;
                this.points.forEach(function(c) {
                    c.graphic && c.hasNewShapeType() && (c.graphic = c.graphic.destroy());
                    c.graphic || (c.graphic = e[c.shapeType](c.shapeArgs).add(c.series.group), c.delayedRendering = !0)
                })
            },
            searchPoint: k,
            sortByAngle: function(e, c) {
                e.sort(function(e, f) {
                    return "undefined" !== typeof e.angle && (f.angle - e.angle) * c
                })
            },
            drawLegendSymbol: m.drawRectangle,
            getCenter: r.getCenter,
            getSymbol: k,
            drawGraph: null
        },
        {
            init: function() {
                y.prototype.init.apply(this, arguments);
                var e = this;
                e.name = z(e.name, "Slice");
                var c = function(c) {
                    e.slice("select" === c.type)
                };
                E(e, "select", c);
                E(e, "unselect", c);
                return e
            },
            isValid: function() {
                return B(this.y) && 0 <= this.y
            },
            setVisible: function(e, c) {
                var g = this,
                f = g.series,
                b = f.chart,
                a = f.options.ignoreHiddenPoint;
                c = z(c, a);
                e !== g.visible && (g.visible = g.options.visible = e = "undefined" === typeof e ? !g.visible: e, f.options.data[f.data.indexOf(g)] = g.options, ["graphic", "dataLabel", "connector", "shadowGroup"].forEach(function(a) {
                    if (g[a]) g[a][e ? "show": "hide"](!0)
                }), g.legendItem && b.legend.colorizeItem(g, e), e || "hover" !== g.state || g.setState(""), a && (f.isDirty = !0), c && b.redraw())
            },
            slice: function(e, c, g) {
                var f = this.series;
                t(g, f.chart);
                z(c, !0);
                this.sliced = this.options.sliced = F(e) ? e: !this.sliced;
                f.options.data[f.data.indexOf(this)] = this.options;
                this.graphic.animate(this.getTranslate());
                this.shadowGroup && this.shadowGroup.animate(this.getTranslate())
            },
            getTranslate: function() {
                return this.sliced ? this.slicedTranslation: {
                    translateX: 0,
                    translateY: 0
                }
            },
            haloPath: function(e) {
                var c = this.shapeArgs;
                return this.sliced || !this.visible ? [] : this.series.chart.renderer.symbols.arc(c.x, c.y, c.r + e, c.r + e, {
                    innerR: c.r - 1,
                    start: c.start,
                    end: c.end
                })
            },
            connectorShapes: {
                fixedOffset: function(e, c, g) {
                    var f = c.breakAt;
                    c = c.touchingSliceAt;
                    return ["M", e.x, e.y].concat(g.softConnector ? ["C", e.x + ("left" === e.alignment ? -5 : 5), e.y, 2 * f.x - c.x, 2 * f.y - c.y, f.x, f.y] : ["L", f.x, f.y]).concat(["L", c.x, c.y])
                },
                straight: function(e, c) {
                    c = c.touchingSliceAt;
                    return ["M", e.x, e.y, "L", c.x, c.y]
                },
                crookedLine: function(e, c, g) {
                    c = c.touchingSliceAt;
                    var f = this.series,
                    b = f.center[0],
                    a = f.chart.plotWidth,
                    k = f.chart.plotLeft;
                    f = e.alignment;
                    var n = this.shapeArgs.r;
                    g = x(g.crookDistance, 1);
                    g = "left" === f ? b + n + (a + k - b - n) * (1 - g) : k + (b - n) * g;
                    b = ["L", g, e.y];
                    if ("left" === f ? g > e.x || g < c.x: g < e.x || g > c.x) b = [];
                    return ["M", e.x, e.y].concat(b).concat(["L", c.x, c.y])
                }
            },
            getConnectorPath: function() {
                var e = this.labelPosition,
                c = this.series.options.dataLabels,
                g = c.connectorShape,
                f = this.connectorShapes;
                f[g] && (g = f[g]);
                return g.call(this, {
                    x: e.final.x,
                    y: e.final.y,
                    alignment: e.alignment
                },
                e.connectorPosition, c)
            }
        });
        ""
    });
    P(y, "parts/DataLabels.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.animObject,
        r = m.arrayMax,
        E = m.clamp,
        N = m.defined,
        F = m.extend,
        C = m.format,
        B = m.isArray,
        L = m.merge,
        z = m.objectEach,
        x = m.pick,
        A = m.relativeLength,
        t = m.splat,
        n = m.stableSort;
        m = f.noop;
        var k = f.Series,
        q = f.seriesTypes;
        f.distribute = function(e, c, g) {
            function p(a, b) {
                return a.target - b.target
            }
            var b, a = !0,
            k = e,
            m = [];
            var w = 0;
            var l = k.reducedLen || c;
            for (b = e.length; b--;) w += e[b].size;
            if (w > l) {
                n(e,
                function(a, b) {
                    return (b.rank || 0) - (a.rank || 0)
                });
                for (w = b = 0; w <= l;) w += e[b].size,
                b++;
                m = e.splice(b - 1, e.length)
            }
            n(e, p);
            for (e = e.map(function(a) {
                return {
                    size: a.size,
                    targets: [a.target],
                    align: x(a.align, .5)
                }
            }); a;) {
                for (b = e.length; b--;) a = e[b],
                w = (Math.min.apply(0, a.targets) + Math.max.apply(0, a.targets)) / 2,
                a.pos = E(w - a.size * a.align, 0, c - a.size);
                b = e.length;
                for (a = !1; b--;) 0 < b && e[b - 1].pos + e[b - 1].size > e[b].pos && (e[b - 1].size += e[b].size, e[b - 1].targets = e[b - 1].targets.concat(e[b].targets), e[b - 1].align = .5, e[b - 1].pos + e[b - 1].size > c && (e[b - 1].pos = c - e[b - 1].size), e.splice(b, 1), a = !0)
            }
            k.push.apply(k, m);
            b = 0;
            e.some(function(a) {
                var e = 0;
                if (a.targets.some(function() {
                    k[b].pos = a.pos + e;
                    if ("undefined" !== typeof g && Math.abs(k[b].pos - k[b].target) > g) return k.slice(0, b + 1).forEach(function(a) {
                        delete a.pos
                    }),
                    k.reducedLen = (k.reducedLen || c) - .1 * c,
                    k.reducedLen > .1 * c && f.distribute(k, c, g),
                    !0;
                    e += k[b].size;
                    b++
                })) return ! 0
            });
            n(k, p)
        };
        k.prototype.drawDataLabels = function() {
            function e(a, b) {
                var d = b.filter;
                return d ? (b = d.operator, a = a[d.property], d = d.value, ">" === b && a > d || "<" === b && a < d || ">=" === b && a >= d || "<=" === b && a <= d || "==" === b && a == d || "===" === b && a === d ? !0 : !1) : !0
            }
            function c(a, b) {
                var d = [],
                c;
                if (B(a) && !B(b)) d = a.map(function(a) {
                    return L(a, b)
                });
                else if (B(b) && !B(a)) d = b.map(function(d) {
                    return L(a, d)
                });
                else if (B(a) || B(b)) for (c = Math.max(a.length, b.length); c--;) d[c] = L(a[c], b[c]);
                else d = L(a, b);
                return d
            }
            var g = this,
            p = g.chart,
            b = g.options,
            a = b.dataLabels,
            k = g.points,
            n, w = g.hasRendered || 0,
            l = y(b.animation).duration,
            h = Math.min(l, 200),
            u = !p.renderer.forExport && x(a.defer, 0 < h),
            m = p.renderer;
            a = c(c(p.options.plotOptions && p.options.plotOptions.series && p.options.plotOptions.series.dataLabels, p.options.plotOptions && p.options.plotOptions[g.type] && p.options.plotOptions[g.type].dataLabels), a);
            f.fireEvent(this, "drawDataLabels");
            if (B(a) || a.enabled || g._hasPointLabels) {
                var q = g.plotGroup("dataLabelsGroup", "data-labels", u && !w ? "hidden": "inherit", a.zIndex || 6);
                u && (q.attr({
                    opacity: +w
                }), w || setTimeout(function() {
                    var a = g.dataLabelsGroup;
                    a && (g.visible && q.show(!0), a[b.animation ? "animate": "attr"]({
                        opacity: 1
                    },
                    {
                        duration: h
                    }))
                },
                l - h));
                k.forEach(function(h) {
                    n = t(c(a, h.dlOptions || h.options && h.options.dataLabels));
                    n.forEach(function(a, d) {
                        var c = a.enabled && (!h.isNull || h.dataLabelOnNull) && e(h, a),
                        l = h.dataLabels ? h.dataLabels[d] : h.dataLabel,
                        f = h.connectors ? h.connectors[d] : h.connector,
                        k = x(a.distance, h.labelDistance),
                        u = !l;
                        if (c) {
                            var n = h.getLabelConfig();
                            var w = x(a[h.formatPrefix + "Format"], a.format);
                            n = N(w) ? C(w, n, p) : (a[h.formatPrefix + "Formatter"] || a.formatter).call(n, a);
                            w = a.style;
                            var v = a.rotation;
                            p.styledMode || (w.color = x(a.color, w.color, g.color, "#000000"), "contrast" === w.color ? (h.contrastColor = m.getContrast(h.color || g.color), w.color = !N(k) && a.inside || 0 > k || b.stacking ? h.contrastColor: "#000000") : delete h.contrastColor, b.cursor && (w.cursor = b.cursor));
                            var H = {
                                r: a.borderRadius || 0,
                                rotation: v,
                                padding: a.padding,
                                zIndex: 1
                            };
                            p.styledMode || (H.fill = a.backgroundColor, H.stroke = a.borderColor, H["stroke-width"] = a.borderWidth);
                            z(H,
                            function(a, d) {
                                "undefined" === typeof a && delete H[d]
                            })
                        } ! l || c && N(n) ? c && N(n) && (l ? H.text = n: (h.dataLabels = h.dataLabels || [], l = h.dataLabels[d] = v ? m.text(n, 0, -9999, a.useHTML).addClass("highcharts-data-label") : m.label(n, 0, -9999, a.shape, null, null, a.useHTML, null, "data-label"), d || (h.dataLabel = l), l.addClass(" highcharts-data-label-color-" + h.colorIndex + " " + (a.className || "") + (a.useHTML ? " highcharts-tracker": ""))), l.options = a, l.attr(H), p.styledMode || l.css(w).shadow(a.shadow), l.added || l.add(q), a.textPath && !a.useHTML && (l.setTextPath(h.getDataLabelPath && h.getDataLabelPath(l) || h.graphic, a.textPath), h.dataLabelPath && !a.textPath.enabled && (h.dataLabelPath = h.dataLabelPath.destroy())), g.alignDataLabel(h, l, a, null, u)) : (h.dataLabel = h.dataLabel && h.dataLabel.destroy(), h.dataLabels && (1 === h.dataLabels.length ? delete h.dataLabels: delete h.dataLabels[d]), d || delete h.dataLabel, f && (h.connector = h.connector.destroy(), h.connectors && (1 === h.connectors.length ? delete h.connectors: delete h.connectors[d])))
                    })
                })
            }
            f.fireEvent(this, "afterDrawDataLabels")
        };
        k.prototype.alignDataLabel = function(e, c, g, f, b) {
            var a = this,
            p = this.chart,
            k = this.isCartesian && p.inverted,
            n = this.enabledDataSorting,
            l = x(e.dlBox && e.dlBox.centerX, e.plotX, -9999),
            h = x(e.plotY, -9999),
            u = c.getBBox(),
            m = g.rotation,
            q = g.align,
            t = p.isInsidePlot(l, Math.round(h), k),
            z = "justify" === x(g.overflow, n ? "none": "justify"),
            d = this.visible && !1 !== e.visible && (e.series.forceDL || n && !z || t || g.inside && f && p.isInsidePlot(l, k ? f.x + 1 : f.y + f.height - 1, k));
            var r = function(d) {
                n && a.xAxis && !z && a.setDataLabelStartPos(e, c, b, t, d)
            };
            if (d) {
                var A = p.renderer.fontMetrics(p.styledMode ? void 0 : g.style.fontSize, c).b;
                f = F({
                    x: k ? this.yAxis.len - h: l,
                    y: Math.round(k ? this.xAxis.len - l: h),
                    width: 0,
                    height: 0
                },
                f);
                F(g, {
                    width: u.width,
                    height: u.height
                });
                m ? (z = !1, l = p.renderer.rotCorr(A, m), l = {
                    x: f.x + g.x + f.width / 2 + l.x,
                    y: f.y + g.y + {
                        top: 0,
                        middle: .5,
                        bottom: 1
                    } [g.verticalAlign] * f.height
                },
                r(l), c[b ? "attr": "animate"](l).attr({
                    align: q
                }), r = (m + 720) % 360, r = 180 < r && 360 > r, "left" === q ? l.y -= r ? u.height: 0 : "center" === q ? (l.x -= u.width / 2, l.y -= u.height / 2) : "right" === q && (l.x -= u.width, l.y -= r ? 0 : u.height), c.placed = !0, c.alignAttr = l) : (r(f), c.align(g, null, f), l = c.alignAttr);
                z && 0 <= f.height ? this.justifyDataLabel(c, g, l, u, f, b) : x(g.crop, !0) && (d = p.isInsidePlot(l.x, l.y) && p.isInsidePlot(l.x + u.width, l.y + u.height));
                if (g.shape && !m) c[b ? "attr": "animate"]({
                    anchorX: k ? p.plotWidth - e.plotY: e.plotX,
                    anchorY: k ? p.plotHeight - e.plotX: e.plotY
                })
            }
            b && n && (c.placed = !1);
            d || n && !z || (c.hide(!0), c.placed = !1)
        };
        k.prototype.setDataLabelStartPos = function(e, c, g, f, b) {
            var a = this.chart,
            p = a.inverted,
            k = this.xAxis,
            n = k.reversed,
            l = p ? c.height / 2 : c.width / 2;
            e = (e = e.pointWidth) ? e / 2 : 0;
            k = p ? b.x: n ? -l - e: k.width - l + e;
            b = p ? n ? this.yAxis.height - l + e: -l - e: b.y;
            c.startXPos = k;
            c.startYPos = b;
            f ? "hidden" === c.visibility && (c.show(), c.attr({
                opacity: 0
            }).animate({
                opacity: 1
            })) : c.attr({
                opacity: 1
            }).animate({
                opacity: 0
            },
            void 0, c.hide);
            a.hasRendered && (g && c.attr({
                x: c.startXPos,
                y: c.startYPos
            }), c.placed = !0)
        };
        k.prototype.justifyDataLabel = function(e, c, g, f, b, a) {
            var p = this.chart,
            k = c.align,
            n = c.verticalAlign,
            l = e.box ? 0 : e.padding || 0;
            var h = g.x + l;
            if (0 > h) {
                "right" === k ? (c.align = "left", c.inside = !0) : c.x = -h;
                var u = !0
            }
            h = g.x + f.width - l;
            h > p.plotWidth && ("left" === k ? (c.align = "right", c.inside = !0) : c.x = p.plotWidth - h, u = !0);
            h = g.y + l;
            0 > h && ("bottom" === n ? (c.verticalAlign = "top", c.inside = !0) : c.y = -h, u = !0);
            h = g.y + f.height - l;
            h > p.plotHeight && ("top" === n ? (c.verticalAlign = "bottom", c.inside = !0) : c.y = p.plotHeight - h, u = !0);
            u && (e.placed = !a, e.align(c, null, b));
            return u
        };
        q.pie && (q.pie.prototype.dataLabelPositioners = {
            radialDistributionY: function(e) {
                return e.top + e.distributeBox.pos
            },
            radialDistributionX: function(e, c, g, f) {
                return e.getX(g < c.top + 2 || g > c.bottom - 2 ? f: g, c.half, c)
            },
            justify: function(e, c, g) {
                return g[0] + (e.half ? -1 : 1) * (c + e.labelDistance)
            },
            alignToPlotEdges: function(e, c, g, f) {
                e = e.getBBox().width;
                return c ? e + f: g - e - f
            },
            alignToConnectors: function(e, c, g, f) {
                var b = 0,
                a;
                e.forEach(function(c) {
                    a = c.dataLabel.getBBox().width;
                    a > b && (b = a)
                });
                return c ? b + f: g - b - f
            }
        },
        q.pie.prototype.drawDataLabels = function() {
            var e = this,
            c = e.data,
            g, p = e.chart,
            b = e.options.dataLabels || {},
            a = b.connectorPadding,
            n, m = p.plotWidth,
            w = p.plotHeight,
            l = p.plotLeft,
            h = Math.round(p.chartWidth / 3),
            u,
            q = e.center,
            t = q[2] / 2,
            z = q[1],
            G,
            d,
            A,
            B,
            C = [[], []],
            y,
            E,
            J,
            F,
            P = [0, 0, 0, 0],
            ba = e.dataLabelPositioners,
            Z;
            e.visible && (b.enabled || e._hasPointLabels) && (c.forEach(function(a) {
                a.dataLabel && a.visible && a.dataLabel.shortened && (a.dataLabel.attr({
                    width: "auto"
                }).css({
                    width: "auto",
                    textOverflow: "clip"
                }), a.dataLabel.shortened = !1)
            }), k.prototype.drawDataLabels.apply(e), c.forEach(function(a) {
                a.dataLabel && (a.visible ? (C[a.half].push(a), a.dataLabel._pos = null, !N(b.style.width) && !N(a.options.dataLabels && a.options.dataLabels.style && a.options.dataLabels.style.width) && a.dataLabel.getBBox().width > h && (a.dataLabel.css({
                    width: .7 * h
                }), a.dataLabel.shortened = !0)) : (a.dataLabel = a.dataLabel.destroy(), a.dataLabels && 1 === a.dataLabels.length && delete a.dataLabels))
            }), C.forEach(function(c, h) {
                var k = c.length,
                u = [],
                n;
                if (k) {
                    e.sortByAngle(c, h - .5);
                    if (0 < e.maxLabelDistance) {
                        var v = Math.max(0, z - t - e.maxLabelDistance);
                        var H = Math.min(z + t + e.maxLabelDistance, p.plotHeight);
                        c.forEach(function(a) {
                            0 < a.labelDistance && a.dataLabel && (a.top = Math.max(0, z - t - a.labelDistance), a.bottom = Math.min(z + t + a.labelDistance, p.plotHeight), n = a.dataLabel.getBBox().height || 21, a.distributeBox = {
                                target: a.labelPosition.natural.y - a.top + n / 2,
                                size: n,
                                rank: a.y
                            },
                            u.push(a.distributeBox))
                        });
                        v = H + n - v;
                        f.distribute(u, v, v / 5)
                    }
                    for (F = 0; F < k; F++) {
                        g = c[F];
                        A = g.labelPosition;
                        G = g.dataLabel;
                        J = !1 === g.visible ? "hidden": "inherit";
                        E = v = A.natural.y;
                        u && N(g.distributeBox) && ("undefined" === typeof g.distributeBox.pos ? J = "hidden": (B = g.distributeBox.size, E = ba.radialDistributionY(g)));
                        delete g.positionIndex;
                        if (b.justify) y = ba.justify(g, t, q);
                        else switch (b.alignTo) {
                        case "connectors":
                            y = ba.alignToConnectors(c, h, m, l);
                            break;
                        case "plotEdges":
                            y = ba.alignToPlotEdges(G, h, m, l);
                            break;
                        default:
                            y = ba.radialDistributionX(e, g, E, v)
                        }
                        G._attr = {
                            visibility: J,
                            align: A.alignment
                        };
                        Z = g.options.dataLabels || {};
                        G._pos = {
                            x: y + x(Z.x, b.x) + ({
                                left: a,
                                right: -a
                            } [A.alignment] || 0),
                            y: E + x(Z.y, b.y) - 10
                        };
                        A.final.x = y;
                        A.final.y = E;
                        x(b.crop, !0) && (d = G.getBBox().width, v = null, y - d < a && 1 === h ? (v = Math.round(d - y + a), P[3] = Math.max(v, P[3])) : y + d > m - a && 0 === h && (v = Math.round(y + d - m + a), P[1] = Math.max(v, P[1])), 0 > E - B / 2 ? P[0] = Math.max(Math.round( - E + B / 2), P[0]) : E + B / 2 > w && (P[2] = Math.max(Math.round(E + B / 2 - w), P[2])), G.sideOverflow = v)
                    }
                }
            }), 0 === r(P) || this.verifyDataLabelOverflow(P)) && (this.placeDataLabels(), this.points.forEach(function(a) {
                Z = L(b, a.options.dataLabels);
                if (n = x(Z.connectorWidth, 1)) {
                    var d;
                    u = a.connector;
                    if ((G = a.dataLabel) && G._pos && a.visible && 0 < a.labelDistance) {
                        J = G._attr.visibility;
                        if (d = !u) a.connector = u = p.renderer.path().addClass("highcharts-data-label-connector  highcharts-color-" + a.colorIndex + (a.className ? " " + a.className: "")).add(e.dataLabelsGroup),
                        p.styledMode || u.attr({
                            "stroke-width": n,
                            stroke: Z.connectorColor || a.color || "#666666"
                        });
                        u[d ? "attr": "animate"]({
                            d: a.getConnectorPath()
                        });
                        u.attr("visibility", J)
                    } else u && (a.connector = u.destroy())
                }
            }))
        },
        q.pie.prototype.placeDataLabels = function() {
            this.points.forEach(function(e) {
                var c = e.dataLabel,
                g;
                c && e.visible && ((g = c._pos) ? (c.sideOverflow && (c._attr.width = Math.max(c.getBBox().width - c.sideOverflow, 0), c.css({
                    width: c._attr.width + "px",
                    textOverflow: (this.options.dataLabels.style || {}).textOverflow || "ellipsis"
                }), c.shortened = !0), c.attr(c._attr), c[c.moved ? "animate": "attr"](g), c.moved = !0) : c && c.attr({
                    y: -9999
                }));
                delete e.distributeBox
            },
            this)
        },
        q.pie.prototype.alignDataLabel = m, q.pie.prototype.verifyDataLabelOverflow = function(e) {
            var c = this.center,
            g = this.options,
            f = g.center,
            b = g.minSize || 80,
            a = null !== g.size;
            if (!a) {
                if (null !== f[0]) var k = Math.max(c[2] - Math.max(e[1], e[3]), b);
                else k = Math.max(c[2] - e[1] - e[3], b),
                c[0] += (e[3] - e[1]) / 2;
                null !== f[1] ? k = E(k, b, c[2] - Math.max(e[0], e[2])) : (k = E(k, b, c[2] - e[0] - e[2]), c[1] += (e[0] - e[2]) / 2);
                k < c[2] ? (c[2] = k, c[3] = Math.min(A(g.innerSize || 0, k), k), this.translate(c), this.drawDataLabels && this.drawDataLabels()) : a = !0
            }
            return a
        });
        q.column && (q.column.prototype.alignDataLabel = function(e, c, g, f, b) {
            var a = this.chart.inverted,
            p = e.series,
            n = e.dlBox || e.shapeArgs,
            m = x(e.below, e.plotY > x(this.translatedThreshold, p.yAxis.len)),
            l = x(g.inside, !!this.options.stacking);
            n && (f = L(n), 0 > f.y && (f.height += f.y, f.y = 0), n = f.y + f.height - p.yAxis.len, 0 < n && n < f.height && (f.height -= n), a && (f = {
                x: p.yAxis.len - f.y - f.height,
                y: p.xAxis.len - f.x - f.width,
                width: f.height,
                height: f.width
            }), l || (a ? (f.x += m ? 0 : f.width, f.width = 0) : (f.y += m ? f.height: 0, f.height = 0)));
            g.align = x(g.align, !a || l ? "center": m ? "right": "left");
            g.verticalAlign = x(g.verticalAlign, a || l ? "middle": m ? "top": "bottom");
            k.prototype.alignDataLabel.call(this, e, c, g, f, b);
            g.inside && e.contrastColor && c.css({
                color: e.contrastColor
            })
        })
    });
    P(y, "modules/overlapping-datalabels.src.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.addEvent,
        r = m.fireEvent,
        E = m.isArray,
        N = m.objectEach,
        F = m.pick;
        f = f.Chart;
        y(f, "render",
        function() {
            var f = []; (this.labelCollectors || []).forEach(function(m) {
                f = f.concat(m())
            }); (this.yAxis || []).forEach(function(m) {
                m.options.stackLabels && !m.options.stackLabels.allowOverlap && N(m.stacks,
                function(m) {
                    N(m,
                    function(m) {
                        f.push(m.label)
                    })
                })
            }); (this.series || []).forEach(function(m) {
                var r = m.options.dataLabels;
                m.visible && (!1 !== r.enabled || m._hasPointLabels) && (m.nodes || m.points).forEach(function(m) {
                    m.visible && (E(m.dataLabels) ? m.dataLabels: m.dataLabel ? [m.dataLabel] : []).forEach(function(x) {
                        var z = x.options;
                        x.labelrank = F(z.labelrank, m.labelrank, m.shapeArgs && m.shapeArgs.height);
                        z.allowOverlap || f.push(x)
                    })
                })
            });
            this.hideOverlappingLabels(f)
        });
        f.prototype.hideOverlappingLabels = function(f) {
            var m = this,
            C = f.length,
            z = m.renderer,
            x, A, t, n = !1;
            var k = function(c) {
                var e = c.box ? 0 : c.padding || 0;
                var f = 0;
                if (c && (!c.alignAttr || c.placed)) {
                    var b = c.alignAttr || {
                        x: c.attr("x"),
                        y: c.attr("y")
                    };
                    var a = c.parentGroup;
                    c.width || (f = c.getBBox(), c.width = f.width, c.height = f.height, f = z.fontMetrics(null, c.element).h);
                    return {
                        x: b.x + (a.translateX || 0) + e,
                        y: b.y + (a.translateY || 0) + e - f,
                        width: c.width - 2 * e,
                        height: c.height - 2 * e
                    }
                }
            };
            for (A = 0; A < C; A++) if (x = f[A]) x.oldOpacity = x.opacity,
            x.newOpacity = 1,
            x.absoluteBox = k(x);
            f.sort(function(c, e) {
                return (e.labelrank || 0) - (c.labelrank || 0)
            });
            for (A = 0; A < C; A++) {
                var q = (k = f[A]) && k.absoluteBox;
                for (x = A + 1; x < C; ++x) {
                    var e = (t = f[x]) && t.absoluteBox; ! q || !e || k === t || 0 === k.newOpacity || 0 === t.newOpacity || e.x > q.x + q.width || e.x + e.width < q.x || e.y > q.y + q.height || e.y + e.height < q.y || ((k.labelrank < t.labelrank ? k: t).newOpacity = 0)
                }
            }
            f.forEach(function(c) {
                var e;
                if (c) {
                    var f = c.newOpacity;
                    c.oldOpacity !== f && (c.alignAttr && c.placed ? (f ? c.show(!0) : e = function() {
                        c.hide(!0);
                        c.placed = !1
                    },
                    n = !0, c.alignAttr.opacity = f, c[c.isOld ? "animate": "attr"](c.alignAttr, null, e), r(m, "afterHideOverlappingLabel")) : c.attr({
                        opacity: f
                    }));
                    c.isOld = !0
                }
            });
            n && r(m, "afterHideAllOverlappingLabels")
        }
    });
    P(y, "parts/Interaction.js", [y["parts/Globals.js"], y["parts/Legend.js"], y["parts/Point.js"], y["parts/Utilities.js"]],
    function(f, m, y, r) {
        var E = r.addEvent,
        J = r.createElement,
        F = r.css,
        C = r.defined,
        B = r.extend,
        L = r.fireEvent,
        z = r.isArray,
        x = r.isFunction,
        A = r.isObject,
        t = r.merge,
        n = r.objectEach,
        k = r.pick;
        r = f.Chart;
        var q = f.defaultOptions,
        e = f.defaultPlotOptions,
        c = f.hasTouch,
        g = f.Series,
        p = f.seriesTypes,
        b = f.svg;
        f = f.TrackerMixin = {
            drawTrackerPoint: function() {
                var a = this,
                b = a.chart,
                e = b.pointer,
                g = function(a) {
                    var b = e.getPointFromEvent(a);
                    "undefined" !== typeof b && (e.isDirectTouch = !0, b.onMouseOver(a))
                },
                l;
                a.points.forEach(function(a) {
                    l = z(a.dataLabels) ? a.dataLabels: a.dataLabel ? [a.dataLabel] : [];
                    a.graphic && (a.graphic.element.point = a);
                    l.forEach(function(b) {
                        b.div ? b.div.point = a: b.element.point = a
                    })
                });
                a._hasTracking || (a.trackerGroups.forEach(function(h) {
                    if (a[h]) {
                        a[h].addClass("highcharts-tracker").on("mouseover", g).on("mouseout",
                        function(a) {
                            e.onTrackerMouseOut(a)
                        });
                        if (c) a[h].on("touchstart", g); ! b.styledMode && a.options.cursor && a[h].css(F).css({
                            cursor: a.options.cursor
                        })
                    }
                }), a._hasTracking = !0);
                L(this, "afterDrawTracker")
            },
            drawTrackerGraph: function() {
                var a = this,
                e = a.options,
                g = e.trackByArea,
                f = [].concat(g ? a.areaPath: a.graphPath),
                l = f.length,
                h = a.chart,
                k = h.pointer,
                p = h.renderer,
                n = h.options.tooltip.snap,
                m = a.tracker,
                q,
                d = function() {
                    if (h.hoverSeries !== a) a.onMouseOver()
                },
                t = "rgba(192,192,192," + (b ? .0001 : .002) + ")";
                if (l && !g) for (q = l + 1; q--;)"M" === f[q] && f.splice(q + 1, 0, f[q + 1] - n, f[q + 2], "L"),
                (q && "M" === f[q] || q === l) && f.splice(q, 0, "L", f[q - 2] + n, f[q - 1]);
                m ? m.attr({
                    d: f
                }) : a.graph && (a.tracker = p.path(f).attr({
                    visibility: a.visible ? "visible": "hidden",
                    zIndex: 2
                }).addClass(g ? "highcharts-tracker-area": "highcharts-tracker-line").add(a.group), h.styledMode || a.tracker.attr({
                    "stroke-linejoin": "round",
                    stroke: t,
                    fill: g ? t: "none",
                    "stroke-width": a.graph.strokeWidth() + (g ? 0 : 2 * n)
                }), [a.tracker, a.markerGroup].forEach(function(a) {
                    a.addClass("highcharts-tracker").on("mouseover", d).on("mouseout",
                    function(a) {
                        k.onTrackerMouseOut(a)
                    });
                    e.cursor && !h.styledMode && a.css({
                        cursor: e.cursor
                    });
                    if (c) a.on("touchstart", d)
                }));
                L(this, "afterDrawTracker")
            }
        };
        p.column && (p.column.prototype.drawTracker = f.drawTrackerPoint);
        p.pie && (p.pie.prototype.drawTracker = f.drawTrackerPoint);
        p.scatter && (p.scatter.prototype.drawTracker = f.drawTrackerPoint);
        B(m.prototype, {
            setItemEvents: function(a, b, c) {
                var e = this,
                g = e.chart.renderer.boxWrapper,
                h = a instanceof y,
                f = "highcharts-legend-" + (h ? "point": "series") + "-active",
                k = e.chart.styledMode; (c ? [b, a.legendSymbol] : [a.legendGroup]).forEach(function(c) {
                    if (c) c.on("mouseover",
                    function() {
                        a.visible && e.allItems.forEach(function(b) {
                            a !== b && b.setState("inactive", !h)
                        });
                        a.setState("hover");
                        a.visible && g.addClass(f);
                        k || b.css(e.options.itemHoverStyle)
                    }).on("mouseout",
                    function() {
                        e.chart.styledMode || b.css(t(a.visible ? e.itemStyle: e.itemHiddenStyle));
                        e.allItems.forEach(function(b) {
                            a !== b && b.setState("", !h)
                        });
                        g.removeClass(f);
                        a.setState()
                    }).on("click",
                    function(b) {
                        var c = function() {
                            a.setVisible && a.setVisible();
                            e.allItems.forEach(function(d) {
                                a !== d && d.setState(a.visible ? "inactive": "", !h)
                            })
                        };
                        g.removeClass(f);
                        b = {
                            browserEvent: b
                        };
                        a.firePointEvent ? a.firePointEvent("legendItemClick", b, c) : L(a, "legendItemClick", b, c)
                    })
                })
            },
            createCheckboxForItem: function(a) {
                a.checkbox = J("input", {
                    type: "checkbox",
                    className: "highcharts-legend-checkbox",
                    checked: a.selected,
                    defaultChecked: a.selected
                },
                this.options.itemCheckboxStyle, this.chart.container);
                E(a.checkbox, "click",
                function(b) {
                    L(a.series || a, "checkboxClick", {
                        checked: b.target.checked,
                        item: a
                    },
                    function() {
                        a.select()
                    })
                })
            }
        });
        B(r.prototype, {
            showResetZoom: function() {
                function a() {
                    b.zoomOut()
                }
                var b = this,
                c = q.lang,
                e = b.options.chart.resetZoomButton,
                g = e.theme,
                h = g.states,
                f = "chart" === e.relativeTo || "spaceBox" === e.relativeTo ? null: "plotBox";
                L(this, "beforeShowResetZoom", null,
                function() {
                    b.resetZoomButton = b.renderer.button(c.resetZoom, null, null, a, g, h && h.hover).attr({
                        align: e.position.align,
                        title: c.resetZoomTitle
                    }).addClass("highcharts-reset-zoom").add().align(e.position, !1, f)
                });
                L(this, "afterShowResetZoom")
            },
            zoomOut: function() {
                L(this, "selection", {
                    resetSelection: !0
                },
                this.zoom)
            },
            zoom: function(a) {
                var b = this,
                c, e = b.pointer,
                g = !1,
                h = b.inverted ? e.mouseDownX: e.mouseDownY; ! a || a.resetSelection ? (b.axes.forEach(function(a) {
                    c = a.zoom()
                }), e.initiated = !1) : a.xAxis.concat(a.yAxis).forEach(function(a) {
                    var l = a.axis,
                    f = b.inverted ? l.left: l.top,
                    k = b.inverted ? f + l.width: f + l.height,
                    d = l.isXAxis,
                    p = !1;
                    if (!d && h >= f && h <= k || d || !C(h)) p = !0;
                    e[d ? "zoomX": "zoomY"] && p && (c = l.zoom(a.min, a.max), l.displayBtn && (g = !0))
                });
                var f = b.resetZoomButton;
                g && !f ? b.showResetZoom() : !g && A(f) && (b.resetZoomButton = f.destroy());
                c && b.redraw(k(b.options.chart.animation, a && a.animation, 100 > b.pointCount))
            },
            pan: function(a, b) {
                var c = this,
                e = c.hoverPoints,
                g = c.options.chart,
                h;
                b = "object" === typeof b ? b: {
                    enabled: b,
                    type: "x"
                };
                g && g.panning && (g.panning = b);
                var f = b.type;
                L(this, "pan", {
                    originalEvent: a
                },
                function() {
                    e && e.forEach(function(a) {
                        a.setState()
                    });
                    var b = [1];
                    "xy" === f ? b = [1, 0] : "y" === f && (b = [0]);
                    b.forEach(function(b) {
                        var e = c[b ? "xAxis": "yAxis"][0],
                        g = e.options,
                        d = e.horiz,
                        l = a[d ? "chartX": "chartY"];
                        d = d ? "mouseDownX": "mouseDownY";
                        var f = c[d],
                        k = (e.pointRange || 0) / 2,
                        p = e.reversed && !c.inverted || !e.reversed && c.inverted ? -1 : 1,
                        n = e.getExtremes(),
                        u = e.toValue(f - l, !0) + k * p;
                        p = e.toValue(f + e.len - l, !0) - k * p;
                        var m = p < u;
                        f = m ? p: u;
                        u = m ? u: p;
                        p = Math.min(n.dataMin, k ? n.min: e.toValue(e.toPixels(n.min) - e.minPixelPadding));
                        k = Math.max(n.dataMax, k ? n.max: e.toValue(e.toPixels(n.max) + e.minPixelPadding));
                        if (!g.ordinal) {
                            b && (g = p - f, 0 < g && (u += g, f = p), g = u - k, 0 < g && (u = k, f -= g));
                            if (e.series.length && f !== n.min && u !== n.max && b || e.panningState && f >= e.panningState.startMin && u <= e.panningState.startMax) e.setExtremes(f, u, !1, !1, {
                                trigger: "pan"
                            }),
                            h = !0;
                            c[d] = l
                        }
                    });
                    h && c.redraw(!1);
                    F(c.container, {
                        cursor: "move"
                    })
                })
            }
        });
        B(y.prototype, {
            select: function(a, b) {
                var c = this,
                e = c.series,
                g = e.chart;
                this.selectedStaging = a = k(a, !c.selected);
                c.firePointEvent(a ? "select": "unselect", {
                    accumulate: b
                },
                function() {
                    c.selected = c.options.selected = a;
                    e.options.data[e.data.indexOf(c)] = c.options;
                    c.setState(a && "select");
                    b || g.getSelectedPoints().forEach(function(a) {
                        var b = a.series;
                        a.selected && a !== c && (a.selected = a.options.selected = !1, b.options.data[b.data.indexOf(a)] = a.options, a.setState(g.hoverPoints && b.options.inactiveOtherPoints ? "inactive": ""), a.firePointEvent("unselect"))
                    })
                });
                delete this.selectedStaging
            },
            onMouseOver: function(a) {
                var b = this.series.chart,
                c = b.pointer;
                a = a ? c.normalize(a) : c.getChartCoordinatesFromPoint(this, b.inverted);
                c.runPointActions(a, this)
            },
            onMouseOut: function() {
                var a = this.series.chart;
                this.firePointEvent("mouseOut");
                this.series.options.inactiveOtherPoints || (a.hoverPoints || []).forEach(function(a) {
                    a.setState()
                });
                a.hoverPoints = a.hoverPoint = null
            },
            importEvents: function() {
                if (!this.hasImportedEvents) {
                    var a = this,
                    b = t(a.series.options.point, a.options).events;
                    a.events = b;
                    n(b,
                    function(b, c) {
                        x(b) && E(a, c, b)
                    });
                    this.hasImportedEvents = !0
                }
            },
            setState: function(a, b) {
                var c = this.series,
                g = this.state,
                f = c.options.states[a || "normal"] || {},
                h = e[c.type].marker && c.options.marker,
                p = h && !1 === h.enabled,
                n = h && h.states && h.states[a || "normal"] || {},
                m = !1 === n.enabled,
                q = c.stateMarkerGraphic,
                v = this.marker || {},
                d = c.chart,
                t = c.halo,
                x,
                z = h && c.markerAttribs;
                a = a || "";
                if (! (a === this.state && !b || this.selected && "select" !== a || !1 === f.enabled || a && (m || p && !1 === n.enabled) || a && v.states && v.states[a] && !1 === v.states[a].enabled)) {
                    this.state = a;
                    z && (x = c.markerAttribs(this, a));
                    if (this.graphic) {
                        g && this.graphic.removeClass("highcharts-point-" + g);
                        a && this.graphic.addClass("highcharts-point-" + a);
                        if (!d.styledMode) {
                            var r = c.pointAttribs(this, a);
                            var A = k(d.options.chart.animation, f.animation);
                            c.options.inactiveOtherPoints && ((this.dataLabels || []).forEach(function(a) {
                                a && a.animate({
                                    opacity: r.opacity
                                },
                                A)
                            }), this.connector && this.connector.animate({
                                opacity: r.opacity
                            },
                            A));
                            this.graphic.animate(r, A)
                        }
                        x && this.graphic.animate(x, k(d.options.chart.animation, n.animation, h.animation));
                        q && q.hide()
                    } else {
                        if (a && n) {
                            g = v.symbol || c.symbol;
                            q && q.currentSymbol !== g && (q = q.destroy());
                            if (x) if (q) q[b ? "animate": "attr"]({
                                x: x.x,
                                y: x.y
                            });
                            else g && (c.stateMarkerGraphic = q = d.renderer.symbol(g, x.x, x.y, x.width, x.height).add(c.markerGroup), q.currentSymbol = g); ! d.styledMode && q && q.attr(c.pointAttribs(this, a))
                        }
                        q && (q[a && this.isInside ? "show": "hide"](), q.element.point = this)
                    }
                    a = f.halo;
                    f = (q = this.graphic || q) && q.visibility || "inherit";
                    a && a.size && q && "hidden" !== f && !this.isCluster ? (t || (c.halo = t = d.renderer.path().add(q.parentGroup)), t.show()[b ? "animate": "attr"]({
                        d: this.haloPath(a.size)
                    }), t.attr({
                        "class": "highcharts-halo highcharts-color-" + k(this.colorIndex, c.colorIndex) + (this.className ? " " + this.className: ""),
                        visibility: f,
                        zIndex: -1
                    }), t.point = this, d.styledMode || t.attr(B({
                        fill: this.color || c.color,
                        "fill-opacity": a.opacity
                    },
                    a.attributes))) : t && t.point && t.point.haloPath && t.animate({
                        d: t.point.haloPath(0)
                    },
                    null, t.hide);
                    L(this, "afterSetState")
                }
            },
            haloPath: function(a) {
                return this.series.chart.renderer.symbols.circle(Math.floor(this.plotX) - a, this.plotY - a, 2 * a, 2 * a)
            }
        });
        B(g.prototype, {
            onMouseOver: function() {
                var a = this.chart,
                b = a.hoverSeries;
                if (b && b !== this) b.onMouseOut();
                this.options.events.mouseOver && L(this, "mouseOver");
                this.setState("hover");
                a.hoverSeries = this
            },
            onMouseOut: function() {
                var a = this.options,
                b = this.chart,
                c = b.tooltip,
                e = b.hoverPoint;
                b.hoverSeries = null;
                if (e) e.onMouseOut();
                this && a.events.mouseOut && L(this, "mouseOut"); ! c || this.stickyTracking || c.shared && !this.noSharedTooltip || c.hide();
                b.series.forEach(function(a) {
                    a.setState("", !0)
                })
            },
            setState: function(a, b) {
                var c = this,
                e = c.options,
                g = c.graph,
                h = e.inactiveOtherPoints,
                f = e.states,
                p = e.lineWidth,
                n = e.opacity,
                m = k(f[a || "normal"] && f[a || "normal"].animation, c.chart.options.chart.animation);
                e = 0;
                a = a || "";
                if (c.state !== a && ([c.group, c.markerGroup, c.dataLabelsGroup].forEach(function(b) {
                    b && (c.state && b.removeClass("highcharts-series-" + c.state), a && b.addClass("highcharts-series-" + a))
                }), c.state = a, !c.chart.styledMode)) {
                    if (f[a] && !1 === f[a].enabled) return;
                    a && (p = f[a].lineWidth || p + (f[a].lineWidthPlus || 0), n = k(f[a].opacity, n));
                    if (g && !g.dashstyle) for (f = {
                        "stroke-width": p
                    },
                    g.animate(f, m); c["zone-graph-" + e];) c["zone-graph-" + e].attr(f),
                    e += 1;
                    h || [c.group, c.markerGroup, c.dataLabelsGroup, c.labelBySeries].forEach(function(a) {
                        a && a.animate({
                            opacity: n
                        },
                        m)
                    })
                }
                b && h && c.points && c.setAllPointsToState(a)
            },
            setAllPointsToState: function(a) {
                this.points.forEach(function(b) {
                    b.setState && b.setState(a)
                })
            },
            setVisible: function(a, b) {
                var c = this,
                e = c.chart,
                g = c.legendItem,
                h = e.options.chart.ignoreHiddenSeries,
                f = c.visible;
                var k = (c.visible = a = c.options.visible = c.userOptions.visible = "undefined" === typeof a ? !f: a) ? "show": "hide"; ["group", "dataLabelsGroup", "markerGroup", "tracker", "tt"].forEach(function(a) {
                    if (c[a]) c[a][k]()
                });
                if (e.hoverSeries === c || (e.hoverPoint && e.hoverPoint.series) === c) c.onMouseOut();
                g && e.legend.colorizeItem(c, a);
                c.isDirty = !0;
                c.options.stacking && e.series.forEach(function(a) {
                    a.options.stacking && a.visible && (a.isDirty = !0)
                });
                c.linkedSeries.forEach(function(b) {
                    b.setVisible(a, !1)
                });
                h && (e.isDirtyBox = !0);
                L(c, k); ! 1 !== b && e.redraw()
            },
            show: function() {
                this.setVisible(!0)
            },
            hide: function() {
                this.setVisible(!1)
            },
            select: function(a) {
                this.selected = a = this.options.selected = "undefined" === typeof a ? !this.selected: a;
                this.checkbox && (this.checkbox.checked = a);
                L(this, a ? "select": "unselect")
            },
            drawTracker: f.drawTrackerGraph
        })
    });
    P(y, "parts/Responsive.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.find,
        r = m.isArray,
        E = m.isObject,
        N = m.merge,
        F = m.objectEach,
        C = m.pick,
        B = m.splat,
        L = m.uniqueKey;
        f = f.Chart;
        f.prototype.setResponsive = function(f, m) {
            var x = this.options.responsive,
            t = [],
            n = this.currentResponsive; ! m && x && x.rules && x.rules.forEach(function(f) {
                "undefined" === typeof f._id && (f._id = L());
                this.matchResponsiveRule(f, t)
            },
            this);
            m = N.apply(0, t.map(function(f) {
                return y(x.rules,
                function(k) {
                    return k._id === f
                }).chartOptions
            }));
            m.isResponsiveOptions = !0;
            t = t.toString() || void 0;
            t !== (n && n.ruleIds) && (n && this.update(n.undoOptions, f, !0), t ? (n = this.currentOptions(m), n.isResponsiveOptions = !0, this.currentResponsive = {
                ruleIds: t,
                mergedOptions: m,
                undoOptions: n
            },
            this.update(m, f, !0)) : this.currentResponsive = void 0)
        };
        f.prototype.matchResponsiveRule = function(f, m) {
            var x = f.condition; (x.callback ||
            function() {
                return this.chartWidth <= C(x.maxWidth, Number.MAX_VALUE) && this.chartHeight <= C(x.maxHeight, Number.MAX_VALUE) && this.chartWidth >= C(x.minWidth, 0) && this.chartHeight >= C(x.minHeight, 0)
            }).call(this) && m.push(f._id)
        };
        f.prototype.currentOptions = function(f) {
            function m(f, k, q, e) {
                var c;
                F(f,
                function(g, f) {
                    if (!e && -1 < z.collectionsWithUpdate.indexOf(f)) for (g = B(g), q[f] = [], c = 0; c < g.length; c++) k[f][c] && (q[f][c] = {},
                    m(g[c], k[f][c], q[f][c], e + 1));
                    else E(g) ? (q[f] = r(g) ? [] : {},
                    m(g, k[f] || {},
                    q[f], e + 1)) : q[f] = "undefined" === typeof k[f] ? null: k[f]
                })
            }
            var z = this,
            t = {};
            m(f, this.options, t, 0);
            return t
        }
    });
    P(y, "masters/highcharts.src.js", [y["parts/Globals.js"]],
    function(f) {
        return f
    });
    P(y, "parts/Scrollbar.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.addEvent,
        r = m.correctFloat,
        E = m.defined,
        N = m.destroyObjectProperties,
        F = m.fireEvent,
        C = m.merge,
        B = m.pick,
        L = m.removeEvent;
        m = f.Axis;
        var z = f.defaultOptions,
        x = f.hasTouch,
        A, t = {
            height: f.isTouchDevice ? 20 : 14,
            barBorderRadius: 0,
            buttonBorderRadius: 0,
            liveRedraw: void 0,
            margin: 10,
            minWidth: 6,
            step: .2,
            zIndex: 3,
            barBackgroundColor: "#cccccc",
            barBorderWidth: 1,
            barBorderColor: "#cccccc",
            buttonArrowColor: "#333333",
            buttonBackgroundColor: "#e6e6e6",
            buttonBorderColor: "#cccccc",
            buttonBorderWidth: 1,
            rifleColor: "#333333",
            trackBackgroundColor: "#f2f2f2",
            trackBorderColor: "#f2f2f2",
            trackBorderWidth: 1
        };
        z.scrollbar = C(!0, t, z.scrollbar);
        f.swapXY = A = function(f, n) {
            var e = f.length;
            if (n) for (n = 0; n < e; n += 3) {
                var c = f[n + 1];
                f[n + 1] = f[n + 2];
                f[n + 2] = c
            }
            return f
        };
        var n = function() {
            function f(f, e, c) {
                this._events = [];
                this.from = this.chartY = this.chartX = 0;
                this.scrollbar = this.group = void 0;
                this.scrollbarButtons = [];
                this.scrollbarGroup = void 0;
                this.scrollbarLeft = 0;
                this.scrollbarRifles = void 0;
                this.scrollbarStrokeWidth = 1;
                this.to = this.size = this.scrollbarTop = 0;
                this.track = void 0;
                this.trackBorderWidth = 1;
                this.userOptions = {};
                this.y = this.x = 0;
                this.chart = c;
                this.options = e;
                this.renderer = c.renderer;
                this.init(f, e, c)
            }
            f.prototype.addEvents = function() {
                var f = this.options.inverted ? [1, 0] : [0, 1],
                e = this.scrollbarButtons,
                c = this.scrollbarGroup.element,
                g = this.track.element,
                k = this.mouseDownHandler.bind(this),
                b = this.mouseMoveHandler.bind(this),
                a = this.mouseUpHandler.bind(this);
                f = [[e[f[0]].element, "click", this.buttonToMinClick.bind(this)], [e[f[1]].element, "click", this.buttonToMaxClick.bind(this)], [g, "click", this.trackClick.bind(this)], [c, "mousedown", k], [c.ownerDocument, "mousemove", b], [c.ownerDocument, "mouseup", a]];
                x && f.push([c, "touchstart", k], [c.ownerDocument, "touchmove", b], [c.ownerDocument, "touchend", a]);
                f.forEach(function(a) {
                    y.apply(null, a)
                });
                this._events = f
            };
            f.prototype.buttonToMaxClick = function(f) {
                var e = (this.to - this.from) * B(this.options.step, .2);
                this.updatePosition(this.from + e, this.to + e);
                F(this, "changed", {
                    from: this.from,
                    to: this.to,
                    trigger: "scrollbar",
                    DOMEvent: f
                })
            };
            f.prototype.buttonToMinClick = function(f) {
                var e = r(this.to - this.from) * B(this.options.step, .2);
                this.updatePosition(r(this.from - e), r(this.to - e));
                F(this, "changed", {
                    from: this.from,
                    to: this.to,
                    trigger: "scrollbar",
                    DOMEvent: f
                })
            };
            f.prototype.cursorToScrollbarPosition = function(f) {
                var e = this.options;
                e = e.minWidth > this.calculatedWidth ? e.minWidth: 0;
                return {
                    chartX: (f.chartX - this.x - this.xOffset) / (this.barWidth - e),
                    chartY: (f.chartY - this.y - this.yOffset) / (this.barWidth - e)
                }
            };
            f.prototype.destroy = function() {
                var f = this.chart.scroller;
                this.removeEvents(); ["track", "scrollbarRifles", "scrollbar", "scrollbarGroup", "group"].forEach(function(e) {
                    this[e] && this[e].destroy && (this[e] = this[e].destroy())
                },
                this);
                f && this === f.scrollbar && (f.scrollbar = null, N(f.scrollbarButtons))
            };
            f.prototype.drawScrollbarButton = function(f) {
                var e = this.renderer,
                c = this.scrollbarButtons,
                g = this.options,
                k = this.size;
                var b = e.g().add(this.group);
                c.push(b);
                b = e.rect().addClass("highcharts-scrollbar-button").add(b);
                this.chart.styledMode || b.attr({
                    stroke: g.buttonBorderColor,
                    "stroke-width": g.buttonBorderWidth,
                    fill: g.buttonBackgroundColor
                });
                b.attr(b.crisp({
                    x: -.5,
                    y: -.5,
                    width: k + 1,
                    height: k + 1,
                    r: g.buttonBorderRadius
                },
                b.strokeWidth()));
                b = e.path(A(["M", k / 2 + (f ? -1 : 1), k / 2 - 3, "L", k / 2 + (f ? -1 : 1), k / 2 + 3, "L", k / 2 + (f ? 2 : -2), k / 2], g.vertical)).addClass("highcharts-scrollbar-arrow").add(c[f]);
                this.chart.styledMode || b.attr({
                    fill: g.buttonArrowColor
                })
            };
            f.prototype.init = function(f, e, c) {
                this.scrollbarButtons = [];
                this.renderer = f;
                this.userOptions = e;
                this.options = C(t, e);
                this.chart = c;
                this.size = B(this.options.size, this.options.height);
                e.enabled && (this.render(), this.addEvents())
            };
            f.prototype.mouseDownHandler = function(f) {
                f = this.chart.pointer.normalize(f);
                f = this.cursorToScrollbarPosition(f);
                this.chartX = f.chartX;
                this.chartY = f.chartY;
                this.initPositions = [this.from, this.to];
                this.grabbedCenter = !0
            };
            f.prototype.mouseMoveHandler = function(f) {
                var e = this.chart.pointer.normalize(f),
                c = this.options.vertical ? "chartY": "chartX",
                g = this.initPositions || []; ! this.grabbedCenter || f.touches && 0 === f.touches[0][c] || (e = this.cursorToScrollbarPosition(e)[c], c = this[c], c = e - c, this.hasDragged = !0, this.updatePosition(g[0] + c, g[1] + c), this.hasDragged && F(this, "changed", {
                    from: this.from,
                    to: this.to,
                    trigger: "scrollbar",
                    DOMType: f.type,
                    DOMEvent: f
                }))
            };
            f.prototype.mouseUpHandler = function(f) {
                this.hasDragged && F(this, "changed", {
                    from: this.from,
                    to: this.to,
                    trigger: "scrollbar",
                    DOMType: f.type,
                    DOMEvent: f
                });
                this.grabbedCenter = this.hasDragged = this.chartX = this.chartY = null
            };
            f.prototype.position = function(f, e, c, g) {
                var k = this.options.vertical,
                b = 0,
                a = this.rendered ? "animate": "attr";
                this.x = f;
                this.y = e + this.trackBorderWidth;
                this.width = c;
                this.xOffset = this.height = g;
                this.yOffset = b;
                k ? (this.width = this.yOffset = c = b = this.size, this.xOffset = e = 0, this.barWidth = g - 2 * c, this.x = f += this.options.margin) : (this.height = this.xOffset = g = e = this.size, this.barWidth = c - 2 * g, this.y += this.options.margin);
                this.group[a]({
                    translateX: f,
                    translateY: this.y
                });
                this.track[a]({
                    width: c,
                    height: g
                });
                this.scrollbarButtons[1][a]({
                    translateX: k ? 0 : c - e,
                    translateY: k ? g - b: 0
                })
            };
            f.prototype.removeEvents = function() {
                this._events.forEach(function(f) {
                    L.apply(null, f)
                });
                this._events.length = 0
            };
            f.prototype.render = function() {
                var f = this.renderer,
                e = this.options,
                c = this.size,
                g = this.chart.styledMode,
                k;
                this.group = k = f.g("scrollbar").attr({
                    zIndex: e.zIndex,
                    translateY: -99999
                }).add();
                this.track = f.rect().addClass("highcharts-scrollbar-track").attr({
                    x: 0,
                    r: e.trackBorderRadius || 0,
                    height: c,
                    width: c
                }).add(k);
                g || this.track.attr({
                    fill: e.trackBackgroundColor,
                    stroke: e.trackBorderColor,
                    "stroke-width": e.trackBorderWidth
                });
                this.trackBorderWidth = this.track.strokeWidth();
                this.track.attr({
                    y: -this.trackBorderWidth % 2 / 2
                });
                this.scrollbarGroup = f.g().add(k);
                this.scrollbar = f.rect().addClass("highcharts-scrollbar-thumb").attr({
                    height: c,
                    width: c,
                    r: e.barBorderRadius || 0
                }).add(this.scrollbarGroup);
                this.scrollbarRifles = f.path(A(["M", -3, c / 4, "L", -3, 2 * c / 3, "M", 0, c / 4, "L", 0, 2 * c / 3, "M", 3, c / 4, "L", 3, 2 * c / 3], e.vertical)).addClass("highcharts-scrollbar-rifles").add(this.scrollbarGroup);
                g || (this.scrollbar.attr({
                    fill: e.barBackgroundColor,
                    stroke: e.barBorderColor,
                    "stroke-width": e.barBorderWidth
                }), this.scrollbarRifles.attr({
                    stroke: e.rifleColor,
                    "stroke-width": 1
                }));
                this.scrollbarStrokeWidth = this.scrollbar.strokeWidth();
                this.scrollbarGroup.translate( - this.scrollbarStrokeWidth % 2 / 2, -this.scrollbarStrokeWidth % 2 / 2);
                this.drawScrollbarButton(0);
                this.drawScrollbarButton(1)
            };
            f.prototype.setRange = function(f, e) {
                var c = this.options,
                g = c.vertical,
                k = c.minWidth,
                b = this.barWidth,
                a, n = !this.rendered || this.hasDragged || this.chart.navigator && this.chart.navigator.hasDragged ? "attr": "animate";
                if (E(b)) {
                    f = Math.max(f, 0);
                    var m = Math.ceil(b * f);
                    this.calculatedWidth = a = r(b * Math.min(e, 1) - m);
                    a < k && (m = (b - k + a) * f, a = k);
                    k = Math.floor(m + this.xOffset + this.yOffset);
                    b = a / 2 - .5;
                    this.from = f;
                    this.to = e;
                    g ? (this.scrollbarGroup[n]({
                        translateY: k
                    }), this.scrollbar[n]({
                        height: a
                    }), this.scrollbarRifles[n]({
                        translateY: b
                    }), this.scrollbarTop = k, this.scrollbarLeft = 0) : (this.scrollbarGroup[n]({
                        translateX: k
                    }), this.scrollbar[n]({
                        width: a
                    }), this.scrollbarRifles[n]({
                        translateX: b
                    }), this.scrollbarLeft = k, this.scrollbarTop = 0);
                    12 >= a ? this.scrollbarRifles.hide() : this.scrollbarRifles.show(!0); ! 1 === c.showFull && (0 >= f && 1 <= e ? this.group.hide() : this.group.show());
                    this.rendered = !0
                }
            };
            f.prototype.trackClick = function(f) {
                var e = this.chart.pointer.normalize(f),
                c = this.to - this.from,
                g = this.y + this.scrollbarTop,
                k = this.x + this.scrollbarLeft;
                this.options.vertical && e.chartY > g || !this.options.vertical && e.chartX > k ? this.updatePosition(this.from + c, this.to + c) : this.updatePosition(this.from - c, this.to - c);
                F(this, "changed", {
                    from: this.from,
                    to: this.to,
                    trigger: "scrollbar",
                    DOMEvent: f
                })
            };
            f.prototype.update = function(f) {
                this.destroy();
                this.init(this.chart.renderer, C(!0, this.options, f), this.chart)
            };
            f.prototype.updatePosition = function(f, e) {
                1 < e && (f = r(1 - r(e - f)), e = 1);
                0 > f && (e = r(e - f), f = 0);
                this.from = f;
                this.to = e
            };
            return f
        } ();
        f.Scrollbar || (y(m, "afterInit",
        function() {
            var k = this;
            k.options && k.options.scrollbar && k.options.scrollbar.enabled && (k.options.scrollbar.vertical = !k.horiz, k.options.startOnTick = k.options.endOnTick = !1, k.scrollbar = new n(k.chart.renderer, k.options.scrollbar, k.chart), y(k.scrollbar, "changed",
            function(n) {
                var e = Math.min(B(k.options.min, k.min), k.min, k.dataMin),
                c = Math.max(B(k.options.max, k.max), k.max, k.dataMax) - e;
                if (k.horiz && !k.reversed || !k.horiz && k.reversed) {
                    var g = e + c * this.to;
                    e += c * this.from
                } else g = e + c * (1 - this.from),
                e += c * (1 - this.to);
                B(this.options.liveRedraw, f.svg && !f.isTouchDevice && !this.chart.isBoosting) || "mouseup" === n.DOMType || !E(n.DOMType) ? k.setExtremes(e, g, !0, "mousemove" !== n.DOMType, n) : this.setRange(this.from, this.to)
            }))
        }), y(m, "afterRender",
        function() {
            var f = Math.min(B(this.options.min, this.min), this.min, B(this.dataMin, this.min)),
            n = Math.max(B(this.options.max, this.max), this.max, B(this.dataMax, this.max)),
            e = this.scrollbar,
            c = this.axisTitleMargin + (this.titleOffset || 0),
            g = this.chart.scrollbarsOffsets,
            p = this.options.margin || 0;
            e && (this.horiz ? (this.opposite || (g[1] += c), e.position(this.left, this.top + this.height + 2 + g[1] - (this.opposite ? p: 0), this.width, this.height), this.opposite || (g[1] += p), c = 1) : (this.opposite && (g[0] += c), e.position(this.left + this.width + 2 + g[0] - (this.opposite ? 0 : p), this.top, this.width, this.height), this.opposite && (g[0] += p), c = 0), g[c] += e.size + e.options.margin, isNaN(f) || isNaN(n) || !E(this.min) || !E(this.max) || this.min === this.max ? e.setRange(0, 1) : (g = (this.min - f) / (n - f), f = (this.max - f) / (n - f), this.horiz && !this.reversed || !this.horiz && this.reversed ? e.setRange(g, f) : e.setRange(1 - f, 1 - g)))
        }), y(m, "afterGetOffset",
        function() {
            var f = this.horiz ? 2 : 1,
            n = this.scrollbar;
            n && (this.chart.scrollbarsOffsets = [0, 0], this.chart.axisOffset[f] += n.size + n.options.margin)
        }), f.Scrollbar = n);
        return f.Scrollbar
    });
    P(y, "parts/Navigator.js", [y["parts/Globals.js"], y["parts/Color.js"], y["parts/Scrollbar.js"], y["parts/Utilities.js"]],
    function(f, m, y, r) {
        m = m.parse;
        var E = r.addEvent,
        J = r.clamp,
        F = r.correctFloat,
        C = r.defined,
        B = r.destroyObjectProperties,
        L = r.erase,
        z = r.extend,
        x = r.find,
        A = r.isArray,
        t = r.isNumber,
        n = r.merge,
        k = r.pick,
        q = r.removeEvent,
        e = r.splat,
        c = f.Axis;
        r = f.Chart;
        var g = f.defaultOptions,
        p = f.hasTouch,
        b = f.isTouchDevice,
        a = f.Series,
        v = function(a) {
            for (var b = [], c = 1; c < arguments.length; c++) b[c - 1] = arguments[c];
            b = [].filter.call(b, t);
            if (b.length) return Math[a].apply(0, b)
        };
        var D = "undefined" === typeof f.seriesTypes.areaspline ? "line": "areaspline";
        z(g, {
            navigator: {
                height: 40,
                margin: 25,
                maskInside: !0,
                handles: {
                    width: 7,
                    height: 15,
                    symbols: ["navigator-handle", "navigator-handle"],
                    enabled: !0,
                    lineWidth: 1,
                    backgroundColor: "#f2f2f2",
                    borderColor: "#999999"
                },
                maskFill: m("#6685c2").setOpacity(.3).get(),
                outlineColor: "#cccccc",
                outlineWidth: 1,
                series: {
                    type: D,
                    fillOpacity: .05,
                    lineWidth: 1,
                    compare: null,
                    dataGrouping: {
                        approximation: "average",
                        enabled: !0,
                        groupPixelWidth: 2,
                        smoothed: !0,
                        units: [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1, 2, 3, 4]], ["week", [1, 2, 3]], ["month", [1, 3, 6]], ["year", null]]
                    },
                    dataLabels: {
                        enabled: !1,
                        zIndex: 2
                    },
                    id: "highcharts-navigator-series",
                    className: "highcharts-navigator-series",
                    lineColor: null,
                    marker: {
                        enabled: !1
                    },
                    threshold: null
                },
                xAxis: {
                    overscroll: 0,
                    className: "highcharts-navigator-xaxis",
                    tickLength: 0,
                    lineWidth: 0,
                    gridLineColor: "#e6e6e6",
                    gridLineWidth: 1,
                    tickPixelInterval: 200,
                    labels: {
                        align: "left",
                        style: {
                            color: "#999999"
                        },
                        x: 3,
                        y: -4
                    },
                    crosshair: !1
                },
                yAxis: {
                    className: "highcharts-navigator-yaxis",
                    gridLineWidth: 0,
                    startOnTick: !1,
                    endOnTick: !1,
                    minPadding: .1,
                    maxPadding: .1,
                    labels: {
                        enabled: !1
                    },
                    crosshair: !1,
                    title: {
                        text: null
                    },
                    tickLength: 0,
                    tickWidth: 0
                }
            }
        });
        f.Renderer.prototype.symbols["navigator-handle"] = function(a, b, c, e, f) {
            a = f.width / 2;
            b = Math.round(a / 3) + .5;
            f = f.height;
            return ["M", -a - 1, .5, "L", a, .5, "L", a, f + .5, "L", -a - 1, f + .5, "L", -a - 1, .5, "M", -b, 4, "L", -b, f - 3, "M", b - 1, 4, "L", b - 1, f - 3]
        };
        c.prototype.toFixedRange = function(a, b, c, e) {
            var h = this.chart && this.chart.fixedRange,
            f = (this.pointRange || 0) / 2;
            a = k(c, this.translate(a, !0, !this.horiz));
            b = k(e, this.translate(b, !0, !this.horiz));
            var g = h && (b - a) / h;
            C(c) || (a = F(a + f));
            C(e) || (b = F(b - f));.7 < g && 1.3 > g && (e ? a = b - h: b = a + h);
            t(a) && t(b) || (a = b = void 0);
            return {
                min: a,
                max: b
            }
        };
        var w = function() {
            function a(a) {
                this.zoomedMin = this.zoomedMax = this.yAxis = this.xAxis = this.top = this.size = this.shades = this.rendered = this.range = this.outlineHeight = this.outline = this.opposite = this.navigatorSize = this.navigatorSeries = this.navigatorOptions = this.navigatorGroup = this.navigatorEnabled = this.left = this.height = this.handles = this.chart = this.baseSeries = void 0;
                this.init(a)
            }
            a.prototype.drawHandle = function(a, b, c, e) {
                var h = this.navigatorOptions.handles.height;
                this.handles[b][e](c ? {
                    translateX: Math.round(this.left + this.height / 2),
                    translateY: Math.round(this.top + parseInt(a, 10) + .5 - h)
                }: {
                    translateX: Math.round(this.left + parseInt(a, 10)),
                    translateY: Math.round(this.top + this.height / 2 - h / 2 - 1)
                })
            };
            a.prototype.drawOutline = function(a, b, c, e) {
                var h = this.navigatorOptions.maskInside,
                f = this.outline.strokeWidth(),
                d = f / 2;
                f = f % 2 / 2;
                var g = this.outlineHeight,
                l = this.scrollbarHeight,
                k = this.size,
                p = this.left - l,
                n = this.top;
                c ? (p -= d, c = n + b + f, b = n + a + f, a = ["M", p + g, n - l - f, "L", p + g, c, "L", p, c, "L", p, b, "L", p + g, b, "L", p + g, n + k + l].concat(h ? ["M", p + g, c - d, "L", p + g, b + d] : [])) : (a += p + l - f, b += p + l - f, n += d, a = ["M", p, n, "L", a, n, "L", a, n + g, "L", b, n + g, "L", b, n, "L", p + k + 2 * l, n].concat(h ? ["M", a - d, n, "L", b + d, n] : []));
                this.outline[e]({
                    d: a
                })
            };
            a.prototype.drawMasks = function(a, b, c, e) {
                var h = this.left,
                f = this.top,
                d = this.height;
                if (c) {
                    var g = [h, h, h];
                    var l = [f, f + a, f + b];
                    var k = [d, d, d];
                    var p = [a, b - a, this.size - b]
                } else g = [h, h + a, h + b],
                l = [f, f, f],
                k = [a, b - a, this.size - b],
                p = [d, d, d];
                this.shades.forEach(function(a, d) {
                    a[e]({
                        x: g[d],
                        y: l[d],
                        width: k[d],
                        height: p[d]
                    })
                })
            };
            a.prototype.renderElements = function() {
                var a = this,
                b = a.navigatorOptions,
                c = b.maskInside,
                e = a.chart,
                f = e.renderer,
                g, d = {
                    cursor: e.inverted ? "ns-resize": "ew-resize"
                };
                a.navigatorGroup = g = f.g("navigator").attr({
                    zIndex: 8,
                    visibility: "hidden"
                }).add(); [!c, c, !c].forEach(function(c, h) {
                    a.shades[h] = f.rect().addClass("highcharts-navigator-mask" + (1 === h ? "-inside": "-outside")).add(g);
                    e.styledMode || a.shades[h].attr({
                        fill: c ? b.maskFill: "rgba(0,0,0,0)"
                    }).css(1 === h && d)
                });
                a.outline = f.path().addClass("highcharts-navigator-outline").add(g);
                e.styledMode || a.outline.attr({
                    "stroke-width": b.outlineWidth,
                    stroke: b.outlineColor
                });
                b.handles.enabled && [0, 1].forEach(function(c) {
                    b.handles.inverted = e.inverted;
                    a.handles[c] = f.symbol(b.handles.symbols[c], -b.handles.width / 2 - 1, 0, b.handles.width, b.handles.height, b.handles);
                    a.handles[c].attr({
                        zIndex: 7 - c
                    }).addClass("highcharts-navigator-handle highcharts-navigator-handle-" + ["left", "right"][c]).add(g);
                    if (!e.styledMode) {
                        var h = b.handles;
                        a.handles[c].attr({
                            fill: h.backgroundColor,
                            stroke: h.borderColor,
                            "stroke-width": h.lineWidth
                        }).css(d)
                    }
                })
            };
            a.prototype.update = function(a) { (this.series || []).forEach(function(a) {
                    a.baseSeries && delete a.baseSeries.navigatorSeries
                });
                this.destroy();
                n(!0, this.chart.options.navigator, this.options, a);
                this.init(this.chart)
            };
            a.prototype.render = function(a, b, c, e) {
                var h = this.chart,
                f = this.scrollbarHeight,
                d, g = this.xAxis,
                l = g.pointRange || 0;
                var p = g.fake ? h.xAxis[0] : g;
                var n = this.navigatorEnabled,
                u, m = this.rendered;
                var q = h.inverted;
                var w = h.xAxis[0].minRange,
                v = h.xAxis[0].options.maxRange;
                if (!this.hasDragged || C(c)) {
                    a = F(a - l / 2);
                    b = F(b + l / 2);
                    if (!t(a) || !t(b)) if (m) c = 0,
                    e = k(g.width, p.width);
                    else return;
                    this.left = k(g.left, h.plotLeft + f + (q ? h.plotWidth: 0));
                    this.size = u = d = k(g.len, (q ? h.plotHeight: h.plotWidth) - 2 * f);
                    h = q ? f: d + 2 * f;
                    c = k(c, g.toPixels(a, !0));
                    e = k(e, g.toPixels(b, !0));
                    t(c) && Infinity !== Math.abs(c) || (c = 0, e = h);
                    a = g.toValue(c, !0);
                    b = g.toValue(e, !0);
                    var x = Math.abs(F(b - a));
                    x < w ? this.grabbedLeft ? c = g.toPixels(b - w - l, !0) : this.grabbedRight && (e = g.toPixels(a + w + l, !0)) : C(v) && F(x - l) > v && (this.grabbedLeft ? c = g.toPixels(b - v - l, !0) : this.grabbedRight && (e = g.toPixels(a + v + l, !0)));
                    this.zoomedMax = J(Math.max(c, e), 0, u);
                    this.zoomedMin = J(this.fixedWidth ? this.zoomedMax - this.fixedWidth: Math.min(c, e), 0, u);
                    this.range = this.zoomedMax - this.zoomedMin;
                    u = Math.round(this.zoomedMax);
                    c = Math.round(this.zoomedMin);
                    n && (this.navigatorGroup.attr({
                        visibility: "visible"
                    }), m = m && !this.hasDragged ? "animate": "attr", this.drawMasks(c, u, q, m), this.drawOutline(c, u, q, m), this.navigatorOptions.handles.enabled && (this.drawHandle(c, 0, q, m), this.drawHandle(u, 1, q, m)));
                    this.scrollbar && (q ? (q = this.top - f, p = this.left - f + (n || !p.opposite ? 0 : (p.titleOffset || 0) + p.axisTitleMargin), f = d + 2 * f) : (q = this.top + (n ? this.height: -f), p = this.left - f), this.scrollbar.position(p, q, h, f), this.scrollbar.setRange(this.zoomedMin / (d || 1), this.zoomedMax / (d || 1)));
                    this.rendered = !0
                }
            };
            a.prototype.addMouseEvents = function() {
                var a = this,
                b = a.chart,
                c = b.container,
                e = [],
                f,
                g;
                a.mouseMoveHandler = f = function(b) {
                    a.onMouseMove(b)
                };
                a.mouseUpHandler = g = function(b) {
                    a.onMouseUp(b)
                };
                e = a.getPartsEvents("mousedown");
                e.push(E(b.renderTo, "mousemove", f), E(c.ownerDocument, "mouseup", g));
                p && (e.push(E(b.renderTo, "touchmove", f), E(c.ownerDocument, "touchend", g)), e.concat(a.getPartsEvents("touchstart")));
                a.eventsToUnbind = e;
                a.series && a.series[0] && e.push(E(a.series[0].xAxis, "foundExtremes",
                function() {
                    b.navigator.modifyNavigatorAxisExtremes()
                }))
            };
            a.prototype.getPartsEvents = function(a) {
                var b = this,
                c = []; ["shades", "handles"].forEach(function(e) {
                    b[e].forEach(function(h, f) {
                        c.push(E(h.element, a,
                        function(a) {
                            b[e + "Mousedown"](a, f)
                        }))
                    })
                });
                return c
            };
            a.prototype.shadesMousedown = function(a, b) {
                a = this.chart.pointer.normalize(a);
                var c = this.chart,
                e = this.xAxis,
                h = this.zoomedMin,
                f = this.left,
                d = this.size,
                g = this.range,
                l = a.chartX;
                c.inverted && (l = a.chartY, f = this.top);
                if (1 === b) this.grabbedCenter = l,
                this.fixedWidth = g,
                this.dragOffset = l - h;
                else {
                    a = l - f - g / 2;
                    if (0 === b) a = Math.max(0, a);
                    else if (2 === b && a + g >= d) if (a = d - g, this.reversedExtremes) {
                        a -= g;
                        var k = this.getUnionExtremes().dataMin
                    } else var p = this.getUnionExtremes().dataMax;
                    a !== h && (this.fixedWidth = g, b = e.toFixedRange(a, a + g, k, p), C(b.min) && c.xAxis[0].setExtremes(Math.min(b.min, b.max), Math.max(b.min, b.max), !0, null, {
                        trigger: "navigator"
                    }))
                }
            };
            a.prototype.handlesMousedown = function(a, b) {
                this.chart.pointer.normalize(a);
                a = this.chart;
                var c = a.xAxis[0],
                e = this.reversedExtremes;
                0 === b ? (this.grabbedLeft = !0, this.otherHandlePos = this.zoomedMax, this.fixedExtreme = e ? c.min: c.max) : (this.grabbedRight = !0, this.otherHandlePos = this.zoomedMin, this.fixedExtreme = e ? c.max: c.min);
                a.fixedRange = null
            };
            a.prototype.onMouseMove = function(a) {
                var c = this,
                e = c.chart,
                h = c.left,
                g = c.navigatorSize,
                l = c.range,
                d = c.dragOffset,
                p = e.inverted;
                a.touches && 0 === a.touches[0].pageX || (a = e.pointer.normalize(a), e = a.chartX, p && (h = c.top, e = a.chartY), c.grabbedLeft ? (c.hasDragged = !0, c.render(0, 0, e - h, c.otherHandlePos)) : c.grabbedRight ? (c.hasDragged = !0, c.render(0, 0, c.otherHandlePos, e - h)) : c.grabbedCenter && (c.hasDragged = !0, e < d ? e = d: e > g + d - l && (e = g + d - l), c.render(0, 0, e - d, e - d + l)), c.hasDragged && c.scrollbar && k(c.scrollbar.options.liveRedraw, f.svg && !b && !this.chart.isBoosting) && (a.DOMType = a.type, setTimeout(function() {
                    c.onMouseUp(a)
                },
                0)))
            };
            a.prototype.onMouseUp = function(a) {
                var b = this.chart,
                c = this.xAxis,
                e = this.scrollbar,
                f = a.DOMEvent || a,
                h = b.inverted,
                d = this.rendered && !this.hasDragged ? "animate": "attr",
                g = Math.round(this.zoomedMax),
                l = Math.round(this.zoomedMin);
                if (this.hasDragged && (!e || !e.hasDragged) || "scrollbar" === a.trigger) {
                    e = this.getUnionExtremes();
                    if (this.zoomedMin === this.otherHandlePos) var k = this.fixedExtreme;
                    else if (this.zoomedMax === this.otherHandlePos) var p = this.fixedExtreme;
                    this.zoomedMax === this.size && (p = this.reversedExtremes ? e.dataMin: e.dataMax);
                    0 === this.zoomedMin && (k = this.reversedExtremes ? e.dataMax: e.dataMin);
                    c = c.toFixedRange(this.zoomedMin, this.zoomedMax, k, p);
                    C(c.min) && b.xAxis[0].setExtremes(Math.min(c.min, c.max), Math.max(c.min, c.max), !0, this.hasDragged ? !1 : null, {
                        trigger: "navigator",
                        triggerOp: "navigator-drag",
                        DOMEvent: f
                    })
                }
                "mousemove" !== a.DOMType && "touchmove" !== a.DOMType && (this.grabbedLeft = this.grabbedRight = this.grabbedCenter = this.fixedWidth = this.fixedExtreme = this.otherHandlePos = this.hasDragged = this.dragOffset = null);
                this.navigatorEnabled && (this.shades && this.drawMasks(l, g, h, d), this.outline && this.drawOutline(l, g, h, d), this.navigatorOptions.handles.enabled && Object.keys(this.handles).length === this.handles.length && (this.drawHandle(l, 0, h, d), this.drawHandle(g, 1, h, d)))
            };
            a.prototype.removeEvents = function() {
                this.eventsToUnbind && (this.eventsToUnbind.forEach(function(a) {
                    a()
                }), this.eventsToUnbind = void 0);
                this.removeBaseSeriesEvents()
            };
            a.prototype.removeBaseSeriesEvents = function() {
                var a = this.baseSeries || [];
                this.navigatorEnabled && a[0] && (!1 !== this.navigatorOptions.adaptToUpdatedData && a.forEach(function(a) {
                    q(a, "updatedData", this.updatedDataHandler)
                },
                this), a[0].xAxis && q(a[0].xAxis, "foundExtremes", this.modifyBaseAxisExtremes))
            };
            a.prototype.init = function(a) {
                var b = a.options,
                e = b.navigator,
                f = e.enabled,
                h = b.scrollbar,
                g = h.enabled;
                b = f ? e.height: 0;
                var d = g ? h.height: 0;
                this.handles = [];
                this.shades = [];
                this.chart = a;
                this.setBaseSeries();
                this.height = b;
                this.scrollbarHeight = d;
                this.scrollbarEnabled = g;
                this.navigatorEnabled = f;
                this.navigatorOptions = e;
                this.scrollbarOptions = h;
                this.outlineHeight = b + d;
                this.opposite = k(e.opposite, !(f || !a.inverted));
                var l = this;
                f = l.baseSeries;
                h = a.xAxis.length;
                g = a.yAxis.length;
                var p = f && f[0] && f[0].xAxis || a.xAxis[0] || {
                    options: {}
                };
                a.isDirtyBox = !0;
                l.navigatorEnabled ? (l.xAxis = new c(a, n({
                    breaks: p.options.breaks,
                    ordinal: p.options.ordinal
                },
                e.xAxis, {
                    id: "navigator-x-axis",
                    yAxis: "navigator-y-axis",
                    isX: !0,
                    type: "datetime",
                    index: h,
                    isInternal: !0,
                    offset: 0,
                    keepOrdinalPadding: !0,
                    startOnTick: !1,
                    endOnTick: !1,
                    minPadding: 0,
                    maxPadding: 0,
                    zoomEnabled: !1
                },
                a.inverted ? {
                    offsets: [d, 0, -d, 0],
                    width: b
                }: {
                    offsets: [0, -d, 0, d],
                    height: b
                })), l.yAxis = new c(a, n(e.yAxis, {
                    id: "navigator-y-axis",
                    alignTicks: !1,
                    offset: 0,
                    index: g,
                    isInternal: !0,
                    zoomEnabled: !1
                },
                a.inverted ? {
                    width: b
                }: {
                    height: b
                })), f || e.series.data ? l.updateNavigatorSeries(!1) : 0 === a.series.length && (l.unbindRedraw = E(a, "beforeRedraw",
                function() {
                    0 < a.series.length && !l.series && (l.setBaseSeries(), l.unbindRedraw())
                })), l.reversedExtremes = a.inverted && !l.xAxis.reversed || !a.inverted && l.xAxis.reversed, l.renderElements(), l.addMouseEvents()) : l.xAxis = {
                    translate: function(b, c) {
                        var e = a.xAxis[0],
                        f = e.getExtremes(),
                        h = e.len - 2 * d,
                        g = v("min", e.options.min, f.dataMin);
                        e = v("max", e.options.max, f.dataMax) - g;
                        return c ? b * e / h + g: h * (b - g) / e
                    },
                    toPixels: function(a) {
                        return this.translate(a)
                    },
                    toValue: function(a) {
                        return this.translate(a, !0)
                    },
                    toFixedRange: c.prototype.toFixedRange,
                    fake: !0
                };
                a.options.scrollbar.enabled && (a.scrollbar = l.scrollbar = new y(a.renderer, n(a.options.scrollbar, {
                    margin: l.navigatorEnabled ? 0 : 10,
                    vertical: a.inverted
                }), a), E(l.scrollbar, "changed",
                function(b) {
                    var d = l.size,
                    c = d * this.to;
                    d *= this.from;
                    l.hasDragged = l.scrollbar.hasDragged;
                    l.render(0, 0, d, c); (a.options.scrollbar.liveRedraw || "mousemove" !== b.DOMType && "touchmove" !== b.DOMType) && setTimeout(function() {
                        l.onMouseUp(b)
                    })
                }));
                l.addBaseSeriesEvents();
                l.addChartEvents()
            };
            a.prototype.getUnionExtremes = function(a) {
                var b = this.chart.xAxis[0],
                c = this.xAxis,
                e = c.options,
                f = b.options,
                h;
                a && null === b.dataMin || (h = {
                    dataMin: k(e && e.min, v("min", f.min, b.dataMin, c.dataMin, c.min)),
                    dataMax: k(e && e.max, v("max", f.max, b.dataMax, c.dataMax, c.max))
                });
                return h
            };
            a.prototype.setBaseSeries = function(a, b) {
                var c = this.chart,
                e = this.baseSeries = [];
                a = a || c.options && c.options.navigator.baseSeries || (c.series.length ? x(c.series,
                function(a) {
                    return ! a.options.isInternal
                }).index: 0); (c.series || []).forEach(function(b, c) {
                    b.options.isInternal || !b.options.showInNavigator && (c !== a && b.options.id !== a || !1 === b.options.showInNavigator) || e.push(b)
                });
                this.xAxis && !this.xAxis.fake && this.updateNavigatorSeries(!0, b)
            };
            a.prototype.updateNavigatorSeries = function(a, b) {
                var c = this,
                f = c.chart,
                h = c.baseSeries,
                l, d, p = c.navigatorOptions.series,
                m, u = {
                    enableMouseTracking: !1,
                    index: null,
                    linkedTo: null,
                    group: "nav",
                    padXAxis: !1,
                    xAxis: "navigator-x-axis",
                    yAxis: "navigator-y-axis",
                    showInLegend: !1,
                    stacking: !1,
                    isInternal: !0,
                    states: {
                        inactive: {
                            opacity: 1
                        }
                    }
                },
                w = c.series = (c.series || []).filter(function(a) {
                    var b = a.baseSeries;
                    return 0 > h.indexOf(b) ? (b && (q(b, "updatedData", c.updatedDataHandler), delete b.navigatorSeries), a.chart && a.destroy(), !1) : !0
                });
                h && h.length && h.forEach(function(a) {
                    var e = a.navigatorSeries,
                    q = z({
                        color: a.color,
                        visible: a.visible
                    },
                    A(p) ? g.navigator.series: p);
                    e && !1 === c.navigatorOptions.adaptToUpdatedData || (u.name = "Navigator " + h.length, l = a.options || {},
                    m = l.navigatorOptions || {},
                    d = n(l, u, q, m), d.pointRange = k(q.pointRange, m.pointRange, g.plotOptions[d.type || "line"].pointRange), q = m.data || q.data, c.hasNavigatorData = c.hasNavigatorData || !!q, d.data = q || l.data && l.data.slice(0), e && e.options ? e.update(d, b) : (a.navigatorSeries = f.initSeries(d), a.navigatorSeries.baseSeries = a, w.push(a.navigatorSeries)))
                });
                if (p.data && (!h || !h.length) || A(p)) c.hasNavigatorData = !1,
                p = e(p),
                p.forEach(function(a, b) {
                    u.name = "Navigator " + (w.length + 1);
                    d = n(g.navigator.series, {
                        color: f.series[b] && !f.series[b].options.isInternal && f.series[b].color || f.options.colors[b] || f.options.colors[0]
                    },
                    u, a);
                    d.data = a.data;
                    d.data && (c.hasNavigatorData = !0, w.push(f.initSeries(d)))
                });
                a && this.addBaseSeriesEvents()
            };
            a.prototype.addBaseSeriesEvents = function() {
                var a = this,
                b = a.baseSeries || [];
                b[0] && b[0].xAxis && E(b[0].xAxis, "foundExtremes", this.modifyBaseAxisExtremes);
                b.forEach(function(b) {
                    E(b, "show",
                    function() {
                        this.navigatorSeries && this.navigatorSeries.setVisible(!0, !1)
                    });
                    E(b, "hide",
                    function() {
                        this.navigatorSeries && this.navigatorSeries.setVisible(!1, !1)
                    }); ! 1 !== this.navigatorOptions.adaptToUpdatedData && b.xAxis && E(b, "updatedData", this.updatedDataHandler);
                    E(b, "remove",
                    function() {
                        this.navigatorSeries && (L(a.series, this.navigatorSeries), C(this.navigatorSeries.options) && this.navigatorSeries.remove(!1), delete this.navigatorSeries)
                    })
                },
                this)
            };
            a.prototype.getBaseSeriesMin = function(a) {
                return this.baseSeries.reduce(function(a, b) {
                    return Math.min(a, b.xData ? b.xData[0] : a)
                },
                a)
            };
            a.prototype.modifyNavigatorAxisExtremes = function() {
                var a = this.xAxis,
                b;
                "undefined" !== typeof a.getExtremes && (!(b = this.getUnionExtremes(!0)) || b.dataMin === a.min && b.dataMax === a.max || (a.min = b.dataMin, a.max = b.dataMax))
            };
            a.prototype.modifyBaseAxisExtremes = function() {
                var a = this.chart.navigator,
                b = this.getExtremes(),
                c = b.dataMin,
                e = b.dataMax;
                b = b.max - b.min;
                var f = a.stickToMin,
                g = a.stickToMax,
                d = k(this.options.overscroll, 0),
                l = a.series && a.series[0],
                p = !!this.setExtremes;
                if (!this.eventArgs || "rangeSelectorButton" !== this.eventArgs.trigger) {
                    if (f) {
                        var n = c;
                        var m = n + b
                    }
                    g && (m = e + d, f || (n = Math.max(m - b, a.getBaseSeriesMin(l && l.xData ? l.xData[0] : -Number.MAX_VALUE))));
                    p && (f || g) && t(n) && (this.min = this.userMin = n, this.max = this.userMax = m)
                }
                a.stickToMin = a.stickToMax = null
            };
            a.prototype.updatedDataHandler = function() {
                var a = this.chart.navigator,
                b = this.navigatorSeries,
                c = a.getBaseSeriesMin(this.xData[0]);
                a.stickToMax = a.reversedExtremes ? 0 === Math.round(a.zoomedMin) : Math.round(a.zoomedMax) >= Math.round(a.size);
                a.stickToMin = t(this.xAxis.min) && this.xAxis.min <= c && (!this.chart.fixedRange || !a.stickToMax);
                b && !a.hasNavigatorData && (b.options.pointStart = this.xData[0], b.setData(this.options.data, !1, null, !1))
            };
            a.prototype.addChartEvents = function() {
                this.eventsToUnbind || (this.eventsToUnbind = []);
                this.eventsToUnbind.push(E(this.chart, "redraw",
                function() {
                    var a = this.navigator,
                    b = a && (a.baseSeries && a.baseSeries[0] && a.baseSeries[0].xAxis || a.scrollbar && this.xAxis[0]);
                    b && a.render(b.min, b.max)
                }), E(this.chart, "getMargins",
                function() {
                    var a = this.navigator,
                    b = a.opposite ? "plotTop": "marginBottom";
                    this.inverted && (b = a.opposite ? "marginRight": "plotLeft");
                    this[b] = (this[b] || 0) + (a.navigatorEnabled || !this.inverted ? a.outlineHeight: 0) + a.navigatorOptions.margin
                }))
            };
            a.prototype.destroy = function() {
                this.removeEvents();
                this.xAxis && (L(this.chart.xAxis, this.xAxis), L(this.chart.axes, this.xAxis));
                this.yAxis && (L(this.chart.yAxis, this.yAxis), L(this.chart.axes, this.yAxis)); (this.series || []).forEach(function(a) {
                    a.destroy && a.destroy()
                });
                "series xAxis yAxis shades outline scrollbarTrack scrollbarRifles scrollbarGroup scrollbar navigatorGroup rendered".split(" ").forEach(function(a) {
                    this[a] && this[a].destroy && this[a].destroy();
                    this[a] = null
                },
                this); [this.handles].forEach(function(a) {
                    B(a)
                },
                this)
            };
            return a
        } ();
        f.Navigator || (f.Navigator = w, E(c, "zoom",
        function(a) {
            var c = this.chart.options,
            e = c.chart.zoomType,
            f = c.chart.pinchType,
            g = c.navigator;
            c = c.rangeSelector;
            this.isXAxis && (g && g.enabled || c && c.enabled) && ("y" === e ? a.zoomed = !1 : (!b && "xy" === e || b && "xy" === f) && this.options.range && (e = this.previousZoom, C(a.newMin) ? this.previousZoom = [this.min, this.max] : e && (a.newMin = e[0], a.newMax = e[1], delete this.previousZoom)));
            "undefined" !== typeof a.zoomed && a.preventDefault()
        }), E(r, "beforeShowResetZoom",
        function() {
            var a = this.options,
            c = a.navigator,
            e = a.rangeSelector;
            if ((c && c.enabled || e && e.enabled) && (!b && "x" === a.chart.zoomType || b && "x" === a.chart.pinchType)) return ! 1
        }), E(r, "beforeRender",
        function() {
            var a = this.options;
            if (a.navigator.enabled || a.scrollbar.enabled) this.scroller = this.navigator = new w(this)
        }), E(r, "afterSetChartSize",
        function() {
            var a = this.legend,
            b = this.navigator;
            if (b) {
                var c = a && a.options;
                var e = b.xAxis;
                var f = b.yAxis;
                var g = b.scrollbarHeight;
                this.inverted ? (b.left = b.opposite ? this.chartWidth - g - b.height: this.spacing[3] + g, b.top = this.plotTop + g) : (b.left = this.plotLeft + g, b.top = b.navigatorOptions.top || this.chartHeight - b.height - g - this.spacing[2] - (this.rangeSelector && this.extraBottomMargin ? this.rangeSelector.getHeight() : 0) - (c && "bottom" === c.verticalAlign && c.enabled && !c.floating ? a.legendHeight + k(c.margin, 10) : 0) - (this.titleOffset ? this.titleOffset[2] : 0));
                e && f && (this.inverted ? e.options.left = f.options.left = b.left: e.options.top = f.options.top = b.top, e.setAxisSize(), f.setAxisSize())
            }
        }), E(r, "update",
        function(a) {
            var b = a.options.navigator || {},
            c = a.options.scrollbar || {};
            this.navigator || this.scroller || !b.enabled && !c.enabled || (n(!0, this.options.navigator, b), n(!0, this.options.scrollbar, c), delete a.options.navigator, delete a.options.scrollbar)
        }), E(r, "afterUpdate",
        function(a) {
            this.navigator || this.scroller || !this.options.navigator.enabled && !this.options.scrollbar.enabled || (this.scroller = this.navigator = new w(this), k(a.redraw, !0) && this.redraw(a.animation))
        }), E(r, "afterAddSeries",
        function() {
            this.navigator && this.navigator.setBaseSeries(null, !1)
        }), E(a, "afterUpdate",
        function() {
            this.chart.navigator && !this.options.isInternal && this.chart.navigator.setBaseSeries(null, !1)
        }), r.prototype.callbacks.push(function(a) {
            var b = a.navigator;
            b && a.xAxis[0] && (a = a.xAxis[0].getExtremes(), b.render(a.min, a.max))
        }));
        f.Navigator = w;
        return f.Navigator
    });
    P(y, "parts/OrdinalAxis.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.addEvent,
        r = m.css,
        E = m.defined,
        N = m.extend,
        F = m.pick,
        C = m.timeUnits,
        B = f.Axis;
        m = f.Chart;
        var L = f.noop;
        y(f.Series, "updatedData",
        function() {
            var f = this.xAxis;
            f && f.options.ordinal && delete f.ordinalIndex
        });
        B.prototype.getTimeTicks = function(f, m, r, t, n, k, q) {
            var e = 0,
            c, g, p = {},
            b = [],
            a = -Number.MAX_VALUE,
            v = this.options.tickPixelInterval,
            x = this.chart.time,
            w = [];
            if (!this.options.ordinal && !this.options.breaks || !n || 3 > n.length || "undefined" === typeof m) return x.getTimeTicks.apply(x, arguments);
            var l = n.length;
            for (c = 0; c < l; c++) {
                var h = c && n[c - 1] > r;
                n[c] < m && (e = c);
                if (c === l - 1 || n[c + 1] - n[c] > 5 * k || h) {
                    if (n[c] > a) {
                        for (g = x.getTimeTicks(f, n[e], n[c], t); g.length && g[0] <= a;) g.shift();
                        g.length && (a = g[g.length - 1]);
                        w.push(b.length);
                        b = b.concat(g)
                    }
                    e = c + 1
                }
                if (h) break
            }
            g = g.info;
            if (q && g.unitRange <= C.hour) {
                c = b.length - 1;
                for (e = 1; e < c; e++) if (x.dateFormat("%d", b[e]) !== x.dateFormat("%d", b[e - 1])) {
                    p[b[e]] = "day";
                    var u = !0
                }
                u && (p[b[0]] = "day");
                g.higherRanks = p
            }
            g.segmentStarts = w;
            b.info = g;
            if (q && E(v)) {
                e = w = b.length;
                u = [];
                var z;
                for (x = []; e--;) c = this.translate(b[e]),
                z && (x[e] = z - c),
                u[e] = z = c;
                x.sort();
                x = x[Math.floor(x.length / 2)];
                x < .6 * v && (x = null);
                e = b[w - 1] > r ? w - 1 : w;
                for (z = void 0; e--;) c = u[e],
                w = Math.abs(z - c),
                z && w < .8 * v && (null === x || w < .8 * x) ? (p[b[e]] && !p[b[e + 1]] ? (w = e + 1, z = c) : w = e, b.splice(w, 1)) : z = c
            }
            return b
        };
        N(B.prototype, {
            beforeSetTickPositions: function() {
                var f = [],
                m,
                r = !1,
                t = this.getExtremes(),
                n = t.min,
                k = t.max,
                q,
                e = this.isXAxis && !!this.options.breaks;
                t = this.options.ordinal;
                var c = Number.MAX_VALUE,
                g = this.chart.options.chart.ignoreHiddenSeries,
                p;
                if (t || e) {
                    this.series.forEach(function(a, k) {
                        m = [];
                        if (! (g && !1 === a.visible || !1 === a.takeOrdinalPosition && !e) && (f = f.concat(a.processedXData), b = f.length, f.sort(function(a, b) {
                            return a - b
                        }), c = Math.min(c, F(a.closestPointRange, c)), b)) {
                            for (k = 0; k < b - 1;) f[k] !== f[k + 1] && m.push(f[k + 1]),
                            k++;
                            m[0] !== f[0] && m.unshift(f[0]);
                            f = m
                        }
                        a.isSeriesBoosting && (p = !0)
                    });
                    p && (f.length = 0);
                    var b = f.length;
                    if (2 < b) {
                        var a = f[1] - f[0];
                        for (q = b - 1; q--&&!r;) f[q + 1] - f[q] !== a && (r = !0); ! this.options.keepOrdinalPadding && (f[0] - n > a || k - f[f.length - 1] > a) && (r = !0)
                    } else this.options.overscroll && (2 === b ? c = f[1] - f[0] : 1 === b ? (c = this.options.overscroll, f = [f[0], f[0] + c]) : c = this.overscrollPointsRange);
                    r ? (this.options.overscroll && (this.overscrollPointsRange = c, f = f.concat(this.getOverscrollPositions())), this.ordinalPositions = f, a = this.ordinal2lin(Math.max(n, f[0]), !0), q = Math.max(this.ordinal2lin(Math.min(k, f[f.length - 1]), !0), 1), this.ordinalSlope = k = (k - n) / (q - a), this.ordinalOffset = n - a * k) : (this.overscrollPointsRange = F(this.closestPointRange, this.overscrollPointsRange), this.ordinalPositions = this.ordinalSlope = this.ordinalOffset = void 0)
                }
                this.isOrdinal = t && r;
                this.groupIntervalFactor = null
            },
            val2lin: function(f, m) {
                var x = this.ordinalPositions;
                if (x) {
                    var t = x.length,
                    n;
                    for (n = t; n--;) if (x[n] === f) {
                        var k = n;
                        break
                    }
                    for (n = t - 1; n--;) if (f > x[n] || 0 === n) {
                        f = (f - x[n]) / (x[n + 1] - x[n]);
                        k = n + f;
                        break
                    }
                    m = m ? k: this.ordinalSlope * (k || 0) + this.ordinalOffset
                } else m = f;
                return m
            },
            lin2val: function(f, m) {
                var x = this.ordinalPositions;
                if (x) {
                    var t = this.ordinalSlope,
                    n = this.ordinalOffset,
                    k = x.length - 1;
                    if (m) if (0 > f) f = x[0];
                    else if (f > k) f = x[k];
                    else {
                        k = Math.floor(f);
                        var q = f - k
                    } else for (; k--;) if (m = t * k + n, f >= m) {
                        t = t * (k + 1) + n;
                        q = (f - m) / (t - m);
                        break
                    }
                    return "undefined" !== typeof q && "undefined" !== typeof x[k] ? x[k] + (q ? q * (x[k + 1] - x[k]) : 0) : f
                }
                return f
            },
            getExtendedPositions: function() {
                var f = this,
                m = f.chart,
                r = f.series[0].currentDataGrouping,
                t = f.ordinalIndex,
                n = r ? r.count + r.unitName: "raw",
                k = f.options.overscroll,
                q = f.getExtremes(),
                e;
                t || (t = f.ordinalIndex = {});
                if (!t[n]) {
                    var c = {
                        series: [],
                        chart: m,
                        getExtremes: function() {
                            return {
                                min: q.dataMin,
                                max: q.dataMax + k
                            }
                        },
                        options: {
                            ordinal: !0
                        },
                        val2lin: B.prototype.val2lin,
                        ordinal2lin: B.prototype.ordinal2lin
                    };
                    f.series.forEach(function(g) {
                        e = {
                            xAxis: c,
                            xData: g.xData.slice(),
                            chart: m,
                            destroyGroupedData: L
                        };
                        e.xData = e.xData.concat(f.getOverscrollPositions());
                        e.options = {
                            dataGrouping: r ? {
                                enabled: !0,
                                forced: !0,
                                approximation: "open",
                                units: [[r.unitName, [r.count]]]
                            }: {
                                enabled: !1
                            }
                        };
                        g.processData.apply(e);
                        c.series.push(e)
                    });
                    f.beforeSetTickPositions.apply(c);
                    t[n] = c.ordinalPositions
                }
                return t[n]
            },
            getOverscrollPositions: function() {
                var f = this.options.overscroll,
                m = this.overscrollPointsRange,
                r = [],
                t = this.dataMax;
                if (E(m)) for (r.push(t); t <= this.dataMax + f;) t += m,
                r.push(t);
                return r
            },
            getGroupIntervalFactor: function(f, m, r) {
                r = r.processedXData;
                var t = r.length,
                n = [];
                var k = this.groupIntervalFactor;
                if (!k) {
                    for (k = 0; k < t - 1; k++) n[k] = r[k + 1] - r[k];
                    n.sort(function(f, e) {
                        return f - e
                    });
                    n = n[Math.floor(t / 2)];
                    f = Math.max(f, r[0]);
                    m = Math.min(m, r[t - 1]);
                    this.groupIntervalFactor = k = t * n / (m - f)
                }
                return k
            },
            postProcessTickInterval: function(f) {
                var m = this.ordinalSlope;
                return m ? this.options.breaks ? this.closestPointRange || f: f / (m / this.closestPointRange) : f
            }
        });
        B.prototype.ordinal2lin = B.prototype.val2lin;
        y(m, "pan",
        function(f) {
            var m = this.xAxis[0],
            z = m.options.overscroll,
            t = f.originalEvent.chartX,
            n = this.options.chart && this.options.chart.panning,
            k = !1;
            if (n && "y" !== n.type && m.options.ordinal && m.series.length) {
                var q = this.mouseDownX,
                e = m.getExtremes(),
                c = e.dataMax,
                g = e.min,
                p = e.max,
                b = this.hoverPoints,
                a = m.closestPointRange || m.overscrollPointsRange;
                q = (q - t) / (m.translationSlope * (m.ordinalSlope || a));
                var v = {
                    ordinalPositions: m.getExtendedPositions()
                };
                a = m.lin2val;
                var D = m.val2lin;
                if (!v.ordinalPositions) k = !0;
                else if (1 < Math.abs(q)) {
                    b && b.forEach(function(a) {
                        a.setState()
                    });
                    if (0 > q) {
                        b = v;
                        var w = m.ordinalPositions ? m: v
                    } else b = m.ordinalPositions ? m: v,
                    w = v;
                    v = w.ordinalPositions;
                    c > v[v.length - 1] && v.push(c);
                    this.fixedRange = p - g;
                    q = m.toFixedRange(null, null, a.apply(b, [D.apply(b, [g, !0]) + q, !0]), a.apply(w, [D.apply(w, [p, !0]) + q, !0]));
                    q.min >= Math.min(e.dataMin, g) && q.max <= Math.max(c, p) + z && m.setExtremes(q.min, q.max, !0, !1, {
                        trigger: "pan"
                    });
                    this.mouseDownX = t;
                    r(this.container, {
                        cursor: "move"
                    })
                }
            } else k = !0;
            k || n && /y/.test(n.type) ? z && (m.max = m.dataMax + z) : f.preventDefault()
        });
        y(B, "foundExtremes",
        function() {
            this.isXAxis && E(this.options.overscroll) && this.max === this.dataMax && (!this.chart.mouseIsDown || this.isInternal) && (!this.eventArgs || this.eventArgs && "navigator" !== this.eventArgs.trigger) && (this.max += this.options.overscroll, !this.isInternal && E(this.userMin) && (this.min += this.options.overscroll))
        });
        y(B, "afterSetScale",
        function() {
            this.horiz && !this.isDirty && (this.isDirty = this.isOrdinal && this.chart.navigator && !this.chart.navigator.adaptToUpdatedData)
        })
    });
    P(y, "modules/broken-axis.src.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.addEvent,
        r = m.extend,
        E = m.find,
        N = m.fireEvent,
        F = m.isArray,
        C = m.pick,
        B = f.Axis;
        m = f.Series;
        var L = function(f, m) {
            return E(m,
            function(m) {
                return m.from < f && f < m.to
            })
        };
        r(B.prototype, {
            isInBreak: function(f, m) {
                var r = f.repeat || Infinity,
                t = f.from,
                n = f.to - f.from;
                m = m >= t ? (m - t) % r: r - (t - m) % r;
                return f.inclusive ? m <= n: m < n && 0 !== m
            },
            isInAnyBreak: function(f, m) {
                var r = this.options.breaks,
                t = r && r.length,
                n;
                if (t) {
                    for (; t--;) if (this.isInBreak(r[t], f)) {
                        var k = !0;
                        n || (n = C(r[t].showPoints, !this.isXAxis))
                    }
                    var q = k && m ? k && !n: k
                }
                return q
            }
        });
        y(B, "afterInit",
        function() {
            "function" === typeof this.setBreaks && this.setBreaks(this.options.breaks, !1)
        });
        y(B, "afterSetTickPositions",
        function() {
            if (this.isBroken) {
                var f = this.tickPositions,
                m = this.tickPositions.info,
                r = [],
                t;
                for (t = 0; t < f.length; t++) this.isInAnyBreak(f[t]) || r.push(f[t]);
                this.tickPositions = r;
                this.tickPositions.info = m
            }
        });
        y(B, "afterSetOptions",
        function() {
            this.isBroken && (this.options.ordinal = !1)
        });
        B.prototype.setBreaks = function(f, m) {
            function r(f) {
                var e = f,
                c;
                for (c = 0; c < n.breakArray.length; c++) {
                    var g = n.breakArray[c];
                    if (g.to <= f) e -= g.len;
                    else if (g.from >= f) break;
                    else if (n.isInBreak(g, f)) {
                        e -= f - g.from;
                        break
                    }
                }
                return e
            }
            function t(f) {
                var e;
                for (e = 0; e < n.breakArray.length; e++) {
                    var c = n.breakArray[e];
                    if (c.from >= f) break;
                    else c.to < f ? f += c.len: n.isInBreak(c, f) && (f += c.len)
                }
                return f
            }
            var n = this,
            k = F(f) && !!f.length;
            n.isDirty = n.isBroken !== k;
            n.isBroken = k;
            n.options.breaks = n.userOptions.breaks = f;
            n.forceRedraw = !0;
            n.series.forEach(function(f) {
                f.isDirty = !0
            });
            k || n.val2lin !== r || (delete n.val2lin, delete n.lin2val);
            k && (n.userOptions.ordinal = !1, n.val2lin = r, n.lin2val = t, n.setExtremes = function(f, e, c, g, k) {
                if (this.isBroken) {
                    for (var b, a = this.options.breaks; b = L(f, a);) f = b.to;
                    for (; b = L(e, a);) e = b.from;
                    e < f && (e = f)
                }
                B.prototype.setExtremes.call(this, f, e, c, g, k)
            },
            n.setAxisTranslation = function(f) {
                B.prototype.setAxisTranslation.call(this, f);
                this.unitLength = null;
                if (this.isBroken) {
                    f = n.options.breaks;
                    var e = [],
                    c = [],
                    g = 0,
                    k,
                    b = n.userMin || n.min,
                    a = n.userMax || n.max,
                    m = C(n.pointRangePadding, 0),
                    q;
                    f.forEach(function(c) {
                        k = c.repeat || Infinity;
                        n.isInBreak(c, b) && (b += c.to % k - b % k);
                        n.isInBreak(c, a) && (a -= a % k - c.from % k)
                    });
                    f.forEach(function(c) {
                        l = c.from;
                        for (k = c.repeat || Infinity; l - k > b;) l -= k;
                        for (; l < b;) l += k;
                        for (q = l; q < a; q += k) e.push({
                            value: q,
                            move: "in"
                        }),
                        e.push({
                            value: q + (c.to - c.from),
                            move: "out",
                            size: c.breakSize
                        })
                    });
                    e.sort(function(a, b) {
                        return a.value === b.value ? ("in" === a.move ? 0 : 1) - ("in" === b.move ? 0 : 1) : a.value - b.value
                    });
                    var w = 0;
                    var l = b;
                    e.forEach(function(a) {
                        w += "in" === a.move ? 1 : -1;
                        1 === w && "in" === a.move && (l = a.value);
                        0 === w && (c.push({
                            from: l,
                            to: a.value,
                            len: a.value - l - (a.size || 0)
                        }), g += a.value - l - (a.size || 0))
                    });
                    n.breakArray = c;
                    n.unitLength = a - b - g + m;
                    N(n, "afterBreaks");
                    n.staticScale ? n.transA = n.staticScale: n.unitLength && (n.transA *= (a - n.min + m) / n.unitLength);
                    m && (n.minPixelPadding = n.transA * n.minPointOffset);
                    n.min = b;
                    n.max = a
                }
            });
            C(m, !0) && this.chart.redraw()
        };
        y(m, "afterGeneratePoints",
        function() {
            var f = this.options.connectNulls,
            m = this.points,
            r = this.xAxis,
            t = this.yAxis;
            if (this.isDirty) for (var n = m.length; n--;) {
                var k = m[n],
                q = !(null === k.y && !1 === f) && (r && r.isInAnyBreak(k.x, !0) || t && t.isInAnyBreak(k.y, !0));
                k.visible = q ? !1 : !1 !== k.options.visible
            }
        });
        y(m, "afterRender",
        function() {
            this.drawBreaks(this.xAxis, ["x"]);
            this.drawBreaks(this.yAxis, C(this.pointArrayMap, ["y"]))
        });
        f.Series.prototype.drawBreaks = function(f, m) {
            var r = this,
            t = r.points,
            n, k, q, e;
            f && m.forEach(function(c) {
                n = f.breakArray || [];
                k = f.isXAxis ? f.min: C(r.options.threshold, f.min);
                t.forEach(function(g) {
                    e = C(g["stack" + c.toUpperCase()], g[c]);
                    n.forEach(function(c) {
                        q = !1;
                        if (k < c.from && e > c.to || k > c.from && e < c.from) q = "pointBreak";
                        else if (k < c.from && e > c.from && e < c.to || k > c.from && e > c.to && e < c.from) q = "pointInBreak";
                        q && N(f, q, {
                            point: g,
                            brk: c
                        })
                    })
                })
            })
        };
        f.Series.prototype.gappedPath = function() {
            var m = this.currentDataGrouping,
            r = m && m.gapSize;
            m = this.options.gapSize;
            var A = this.points.slice(),
            t = A.length - 1,
            n = this.yAxis,
            k;
            if (m && 0 < t) for ("value" !== this.options.gapUnit && (m *= this.basePointRange), r && r > m && r >= this.basePointRange && (m = r), k = void 0; t--;) k && !1 !== k.visible || (k = A[t + 1]),
            r = A[t],
            !1 !== k.visible && !1 !== r.visible && (k.x - r.x > m && (k = (r.x + k.x) / 2, A.splice(t + 1, 0, {
                isNull: !0,
                x: k
            }), this.options.stacking && (k = n.stacks[this.stackKey][k] = new f.StackItem(n, n.options.stackLabels, !1, k, this.stack), k.total = 0)), k = r);
            return this.getGraphPath(A)
        }
    });
    P(y, "masters/modules/broken-axis.src.js", [],
    function() {});
    P(y, "parts/DataGrouping.js", [y["parts/Globals.js"], y["parts/Point.js"], y["parts/Tooltip.js"], y["parts/Utilities.js"]],
    function(f, m, y, r) {
        "";
        var E = r.addEvent,
        J = r.arrayMax,
        F = r.arrayMin,
        C = r.correctFloat,
        B = r.defined,
        L = r.error,
        z = r.extend,
        x = r.format,
        A = r.isNumber,
        t = r.merge,
        n = r.pick,
        k = f.Axis,
        q = f.defaultPlotOptions;
        r = f.Series;
        var e = f.approximations = {
            sum: function(a) {
                var b = a.length;
                if (!b && a.hasNulls) var c = null;
                else if (b) for (c = 0; b--;) c += a[b];
                return c
            },
            average: function(a) {
                var b = a.length;
                a = e.sum(a);
                A(a) && b && (a = C(a / b));
                return a
            },
            averages: function() {
                var a = []; [].forEach.call(arguments,
                function(b) {
                    a.push(e.average(b))
                });
                return "undefined" === typeof a[0] ? void 0 : a
            },
            open: function(a) {
                return a.length ? a[0] : a.hasNulls ? null: void 0
            },
            high: function(a) {
                return a.length ? J(a) : a.hasNulls ? null: void 0
            },
            low: function(a) {
                return a.length ? F(a) : a.hasNulls ? null: void 0
            },
            close: function(a) {
                return a.length ? a[a.length - 1] : a.hasNulls ? null: void 0
            },
            ohlc: function(a, b, c, f) {
                a = e.open(a);
                b = e.high(b);
                c = e.low(c);
                f = e.close(f);
                if (A(a) || A(b) || A(c) || A(f)) return [a, b, c, f]
            },
            range: function(a, b) {
                a = e.low(a);
                b = e.high(b);
                if (A(a) || A(b)) return [a, b];
                if (null === a && null === b) return null
            }
        },
        c = function(a, b, c, f) {
            var g = this,
            h = g.data,
            l = g.options && g.options.data,
            d = [],
            k = [],
            p = [],
            n = a.length,
            m = !!b,
            u = [],
            q = g.pointArrayMap,
            w = q && q.length,
            v = ["x"].concat(q || ["y"]),
            r = 0,
            x = 0,
            z;
            f = "function" === typeof f ? f: e[f] ? e[f] : e[g.getDGApproximation && g.getDGApproximation() || "average"];
            w ? q.forEach(function() {
                u.push([])
            }) : u.push([]);
            var H = w || 1;
            for (z = 0; z <= n && !(a[z] >= c[0]); z++);
            for (z; z <= n; z++) {
                for (;
                "undefined" !== typeof c[r + 1] && a[z] >= c[r + 1] || z === n;) {
                    var y = c[r];
                    g.dataGroupInfo = {
                        start: g.cropStart + x,
                        length: u[0].length
                    };
                    var D = f.apply(g, u);
                    g.pointClass && !B(g.dataGroupInfo.options) && (g.dataGroupInfo.options = t(g.pointClass.prototype.optionsToObject.call({
                        series: g
                    },
                    g.options.data[g.cropStart + x])), v.forEach(function(a) {
                        delete g.dataGroupInfo.options[a]
                    }));
                    "undefined" !== typeof D && (d.push(y), k.push(D), p.push(g.dataGroupInfo));
                    x = z;
                    for (y = 0; y < H; y++) u[y].length = 0,
                    u[y].hasNulls = !1;
                    r += 1;
                    if (z === n) break
                }
                if (z === n) break;
                if (q) for (y = g.cropStart + z, D = h && h[y] || g.pointClass.prototype.applyOptions.apply({
                    series: g
                },
                [l[y]]), y = 0; y < w; y++) {
                    var C = D[q[y]];
                    A(C) ? u[y].push(C) : null === C && (u[y].hasNulls = !0)
                } else y = m ? b[z] : null,
                A(y) ? u[0].push(y) : null === y && (u[0].hasNulls = !0)
            }
            return {
                groupedXData: d,
                groupedYData: k,
                groupMap: p
            }
        },
        g = {
            approximations: e,
            groupData: c
        },
        p = r.prototype,
        b = p.processData,
        a = p.generatePoints,
        v = {
            groupPixelWidth: 2,
            dateTimeLabelFormats: {
                millisecond: ["%A, %b %e, %H:%M:%S.%L", "%A, %b %e, %H:%M:%S.%L", "-%H:%M:%S.%L"],
                second: ["%A, %b %e, %H:%M:%S", "%A, %b %e, %H:%M:%S", "-%H:%M:%S"],
                minute: ["%A, %b %e, %H:%M", "%A, %b %e, %H:%M", "-%H:%M"],
                hour: ["%A, %b %e, %H:%M", "%A, %b %e, %H:%M", "-%H:%M"],
                day: ["%A, %b %e, %Y", "%A, %b %e", "-%A, %b %e, %Y"],
                week: ["Week from %A, %b %e, %Y", "%A, %b %e", "-%A, %b %e, %Y"],
                month: ["%B %Y", "%B", "-%B %Y"],
                year: ["%Y", "%Y", "-%Y"]
            }
        },
        D = {
            line: {},
            spline: {},
            area: {},
            areaspline: {},
            arearange: {},
            column: {
                groupPixelWidth: 10
            },
            columnrange: {
                groupPixelWidth: 10
            },
            candlestick: {
                groupPixelWidth: 10
            },
            ohlc: {
                groupPixelWidth: 5
            }
        },
        w = f.defaultDataGroupingUnits = [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1]], ["week", [1]], ["month", [1, 3, 6]], ["year", null]];
        p.getDGApproximation = function() {
            return this.is("arearange") ? "range": this.is("ohlc") ? "ohlc": this.is("column") ? "sum": "average"
        };
        p.groupData = c;
        p.processData = function() {
            var a = this.chart,
            c = this.options.dataGrouping,
            e = !1 !== this.allowDG && c && n(c.enabled, a.options.isStock),
            f = this.visible || !a.options.chart.ignoreHiddenSeries,
            g,
            k = this.currentDataGrouping,
            m = !1;
            this.forceCrop = e;
            this.groupPixelWidth = null;
            this.hasProcessed = !0;
            e && !this.requireSorting && (this.requireSorting = m = !0);
            e = !1 === b.apply(this, arguments) || !e;
            m && (this.requireSorting = !1);
            if (!e) {
                this.destroyGroupedData();
                e = c.groupAll ? this.xData: this.processedXData;
                var d = c.groupAll ? this.yData: this.processedYData,
                q = a.plotSizeX;
                a = this.xAxis;
                var v = a.options.ordinal,
                t = this.groupPixelWidth = a.getGroupPixelWidth && a.getGroupPixelWidth();
                if (t) {
                    this.isDirty = g = !0;
                    this.points = null;
                    m = a.getExtremes();
                    var r = m.min;
                    m = m.max;
                    v = v && a.getGroupIntervalFactor(r, m, this) || 1;
                    t = t * (m - r) / q * v;
                    q = a.getTimeTicks(a.normalizeTimeTickInterval(t, c.units || w), Math.min(r, e[0]), Math.max(m, e[e.length - 1]), a.options.startOfWeek, e, this.closestPointRange);
                    d = p.groupData.apply(this, [e, d, q, c.approximation]);
                    e = d.groupedXData;
                    v = d.groupedYData;
                    var x = 0;
                    if (c.smoothed && e.length) {
                        var z = e.length - 1;
                        for (e[z] = Math.min(e[z], m); z--&&0 < z;) e[z] += t / 2;
                        e[0] = Math.max(e[0], r)
                    }
                    for (z = 1; z < q.length; z++) q.info.segmentStarts && -1 !== q.info.segmentStarts.indexOf(z) || (x = Math.max(q[z] - q[z - 1], x));
                    r = q.info;
                    r.gapSize = x;
                    this.closestPointRange = q.info.totalRange;
                    this.groupMap = d.groupMap;
                    if (B(e[0]) && e[0] < a.min && f) {
                        if (!B(a.options.min) && a.min <= a.dataMin || a.min === a.dataMin) a.min = Math.min(e[0], a.min);
                        a.dataMin = Math.min(e[0], a.dataMin)
                    }
                    c.groupAll && (c = this.cropData(e, v, a.min, a.max, 1), e = c.xData, v = c.yData);
                    this.processedXData = e;
                    this.processedYData = v
                } else this.groupMap = null;
                this.hasGroupedData = g;
                this.currentDataGrouping = r;
                this.preventGraphAnimation = (k && k.totalRange) !== (r && r.totalRange)
            }
        };
        p.destroyGroupedData = function() {
            this.groupedData && (this.groupedData.forEach(function(a, b) {
                a && (this.groupedData[b] = a.destroy ? a.destroy() : null)
            },
            this), this.groupedData.length = 0)
        };
        p.generatePoints = function() {
            a.apply(this);
            this.destroyGroupedData();
            this.groupedData = this.hasGroupedData ? this.points: null
        };
        E(m, "update",
        function() {
            if (this.dataGroup) return L(24, !1, this.series.chart),
            !1
        });
        E(y, "headerFormatter",
        function(a) {
            var b = this.chart,
            c = b.time,
            e = a.labelConfig,
            f = e.series,
            g = f.tooltipOptions,
            l = f.options.dataGrouping,
            d = g.xDateFormat,
            k = f.xAxis,
            p = g[(a.isFooter ? "footer": "header") + "Format"];
            if (k && "datetime" === k.options.type && l && A(e.key)) {
                var n = f.currentDataGrouping;
                l = l.dateTimeLabelFormats || v.dateTimeLabelFormats;
                if (n) if (g = l[n.unitName], 1 === n.count) d = g[0];
                else {
                    d = g[1];
                    var m = g[2]
                } else ! d && l && (d = this.getXDateFormat(e, g, k));
                d = c.dateFormat(d, e.key);
                m && (d += c.dateFormat(m, e.key + n.totalRange - 1));
                f.chart.styledMode && (p = this.styledModeFormat(p));
                a.text = x(p, {
                    point: z(e.point, {
                        key: d
                    }),
                    series: f
                },
                b);
                a.preventDefault()
            }
        });
        E(r, "destroy", p.destroyGroupedData);
        E(r, "afterSetOptions",
        function(a) {
            a = a.options;
            var b = this.type,
            c = this.chart.options.plotOptions,
            e = q[b].dataGrouping,
            f = this.useCommonDataGrouping && v;
            if (D[b] || f) e || (e = t(v, D[b])),
            a.dataGrouping = t(f, e, c.series && c.series.dataGrouping, c[b].dataGrouping, this.userOptions.dataGrouping)
        });
        E(k, "afterSetScale",
        function() {
            this.series.forEach(function(a) {
                a.hasProcessed = !1
            })
        });
        k.prototype.getGroupPixelWidth = function() {
            var a = this.series,
            b = a.length,
            c, e = 0,
            f = !1,
            g;
            for (c = b; c--;)(g = a[c].options.dataGrouping) && (e = Math.max(e, n(g.groupPixelWidth, v.groupPixelWidth)));
            for (c = b; c--;)(g = a[c].options.dataGrouping) && a[c].hasProcessed && (b = (a[c].processedXData || a[c].data).length, a[c].groupPixelWidth || b > this.chart.plotSizeX / e || b && g.forced) && (f = !0);
            return f ? e: 0
        };
        k.prototype.setDataGrouping = function(a, b) {
            var c;
            b = n(b, !0);
            a || (a = {
                forced: !1,
                units: null
            });
            if (this instanceof k) for (c = this.series.length; c--;) this.series[c].update({
                dataGrouping: a
            },
            !1);
            else this.chart.options.series.forEach(function(b) {
                b.dataGrouping = a
            },
            !1);
            this.ordinalSlope = null;
            b && this.chart.redraw()
        };
        f.dataGrouping = g;
        "";
        return g
    });
    P(y, "parts/OHLCSeries.js", [y["parts/Globals.js"], y["parts/Point.js"], y["parts/Utilities.js"]],
    function(f, m, y) {
        y = y.seriesType;
        var r = f.seriesTypes;
        y("ohlc", "column", {
            lineWidth: 1,
            tooltip: {
                pointFormat: '<span style="color:{point.color}">\u25cf</span> <b> {series.name}</b><br/>Open: {point.open}<br/>High: {point.high}<br/>Low: {point.low}<br/>Close: {point.close}<br/>'
            },
            threshold: null,
            states: {
                hover: {
                    lineWidth: 3
                }
            },
            stickyTracking: !0
        },
        {
            directTouch: !1,
            pointArrayMap: ["open", "high", "low", "close"],
            toYData: function(f) {
                return [f.open, f.high, f.low, f.close]
            },
            pointValKey: "close",
            pointAttrToOptions: {
                stroke: "color",
                "stroke-width": "lineWidth"
            },
            init: function() {
                r.column.prototype.init.apply(this, arguments);
                this.options.stacking = !1
            },
            pointAttribs: function(f, m) {
                m = r.column.prototype.pointAttribs.call(this, f, m);
                var y = this.options;
                delete m.fill; ! f.options.color && y.upColor && f.open < f.close && (m.stroke = y.upColor);
                return m
            },
            translate: function() {
                var f = this,
                m = f.yAxis,
                y = !!f.modifyValue,
                C = ["plotOpen", "plotHigh", "plotLow", "plotClose", "yBottom"];
                r.column.prototype.translate.apply(f);
                f.points.forEach(function(r) { [r.open, r.high, r.low, r.close, r.low].forEach(function(B, z) {
                        null !== B && (y && (B = f.modifyValue(B)), r[C[z]] = m.toPixels(B, !0))
                    });
                    r.tooltipPos[1] = r.plotHigh + m.pos - f.chart.plotTop
                })
            },
            drawPoints: function() {
                var f = this,
                m = f.chart,
                r = function(f, m, r) {
                    f[2] = Math.max(r + m, f[2]);
                    f[5] = Math.min(r - m, f[5])
                };
                f.points.forEach(function(y) {
                    var B = y.graphic,
                    C = !B;
                    if ("undefined" !== typeof y.plotY) {
                        B || (y.graphic = B = m.renderer.path().add(f.group));
                        m.styledMode || B.attr(f.pointAttribs(y, y.selected && "select"));
                        var z = B.strokeWidth();
                        var x = z % 2 / 2;
                        var A = Math.round(y.plotX) - x;
                        var t = Math.round(y.shapeArgs.width / 2);
                        var n = ["M", A, Math.round(y.yBottom), "L", A, Math.round(y.plotHigh)];
                        if (null !== y.open) {
                            var k = Math.round(y.plotOpen) + x;
                            n.push("M", A, k, "L", A - t, k);
                            r(n, z / 2, k)
                        }
                        null !== y.close && (k = Math.round(y.plotClose) + x, n.push("M", A, k, "L", A + t, k), r(n, z / 2, k));
                        B[C ? "attr": "animate"]({
                            d: n
                        }).addClass(y.getClassName(), !0)
                    }
                })
            },
            animate: null
        },
        {
            getClassName: function() {
                return m.prototype.getClassName.call(this) + (this.open < this.close ? " highcharts-point-up": " highcharts-point-down")
            }
        });
        ""
    });
    P(y, "parts/CandlestickSeries.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.merge;
        m = m.seriesType;
        var r = f.defaultPlotOptions,
        E = f.seriesTypes;
        m("candlestick", "ohlc", y(r.column, {
            states: {
                hover: {
                    lineWidth: 2
                }
            },
            tooltip: r.ohlc.tooltip,
            threshold: null,
            lineColor: "#000000",
            lineWidth: 1,
            upColor: "#ffffff",
            stickyTracking: !0
        }), {
            pointAttribs: function(f, m) {
                var r = E.column.prototype.pointAttribs.call(this, f, m),
                y = this.options,
                F = f.open < f.close,
                z = y.lineColor || this.color;
                r["stroke-width"] = y.lineWidth;
                r.fill = f.options.color || (F ? y.upColor || this.color: this.color);
                r.stroke = f.options.lineColor || (F ? y.upLineColor || z: z);
                m && (f = y.states[m], r.fill = f.color || r.fill, r.stroke = f.lineColor || r.stroke, r["stroke-width"] = f.lineWidth || r["stroke-width"]);
                return r
            },
            drawPoints: function() {
                var f = this,
                m = f.chart,
                r = f.yAxis.reversed;
                f.points.forEach(function(y) {
                    var B = y.graphic,
                    z = !B;
                    if ("undefined" !== typeof y.plotY) {
                        B || (y.graphic = B = m.renderer.path().add(f.group));
                        f.chart.styledMode || B.attr(f.pointAttribs(y, y.selected && "select")).shadow(f.options.shadow);
                        var x = B.strokeWidth() % 2 / 2;
                        var A = Math.round(y.plotX) - x;
                        var t = y.plotOpen;
                        var n = y.plotClose;
                        var k = Math.min(t, n);
                        t = Math.max(t, n);
                        var q = Math.round(y.shapeArgs.width / 2);
                        n = r ? t !== y.yBottom: Math.round(k) !== Math.round(y.plotHigh);
                        var e = r ? Math.round(k) !== Math.round(y.plotHigh) : t !== y.yBottom;
                        k = Math.round(k) + x;
                        t = Math.round(t) + x;
                        x = [];
                        x.push("M", A - q, t, "L", A - q, k, "L", A + q, k, "L", A + q, t, "Z", "M", A, k, "L", A, n ? Math.round(r ? y.yBottom: y.plotHigh) : k, "M", A, t, "L", A, e ? Math.round(r ? y.plotHigh: y.yBottom) : t);
                        B[z ? "attr": "animate"]({
                            d: x
                        }).addClass(y.getClassName(), !0)
                    }
                })
            }
        });
        ""
    });
    P(y, "mixins/on-series.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        var y = m.defined,
        r = m.stableSort,
        E = f.seriesTypes;
        return {
            getPlotBox: function() {
                return f.Series.prototype.getPlotBox.call(this.options.onSeries && this.chart.get(this.options.onSeries) || this)
            },
            translate: function() {
                E.column.prototype.translate.apply(this);
                var f = this,
                m = f.options,
                C = f.chart,
                B = f.points,
                J = B.length - 1,
                z, x = m.onSeries;
                x = x && C.get(x);
                m = m.onKey || "y";
                var A = x && x.options.step,
                t = x && x.points,
                n = t && t.length,
                k = C.inverted,
                q = f.xAxis,
                e = f.yAxis,
                c = 0,
                g;
                if (x && x.visible && n) {
                    c = (x.pointXOffset || 0) + (x.barW || 0) / 2;
                    C = x.currentDataGrouping;
                    var p = t[n - 1].x + (C ? C.totalRange: 0);
                    r(B,
                    function(a, b) {
                        return a.x - b.x
                    });
                    for (m = "plot" + m[0].toUpperCase() + m.substr(1); n--&&B[J];) {
                        var b = t[n];
                        C = B[J];
                        C.y = b.y;
                        if (b.x <= C.x && "undefined" !== typeof b[m]) {
                            if (C.x <= p && (C.plotY = b[m], b.x < C.x && !A && (g = t[n + 1]) && "undefined" !== typeof g[m])) {
                                var a = (C.x - b.x) / (g.x - b.x);
                                C.plotY += a * (g[m] - b[m]);
                                C.y += a * (g.y - b.y)
                            }
                            J--;
                            n++;
                            if (0 > J) break
                        }
                    }
                }
                B.forEach(function(a, b) {
                    a.plotX += c;
                    if ("undefined" === typeof a.plotY || k) 0 <= a.plotX && a.plotX <= q.len ? k ? (a.plotY = q.translate(a.x, 0, 1, 0, 1), a.plotX = y(a.y) ? e.translate(a.y, 0, 0, 0, 1) : 0) : a.plotY = (q.opposite ? 0 : f.yAxis.len) + q.offset: a.shapeArgs = {};
                    if ((z = B[b - 1]) && z.plotX === a.plotX) {
                        "undefined" === typeof z.stackIndex && (z.stackIndex = 0);
                        var g = z.stackIndex + 1
                    }
                    a.stackIndex = g
                });
                this.onSeries = x
            }
        }
    });
    P(y, "parts/FlagsSeries.js", [y["parts/Globals.js"], y["parts/Utilities.js"], y["mixins/on-series.js"]],
    function(f, m, y) {
        function r(f) {
            k[f + "pin"] = function(e, c, g, p, b) {
                var a = b && b.anchorX;
                b = b && b.anchorY;
                "circle" === f && p > g && (e -= Math.round((p - g) / 2), g = p);
                var n = k[f](e, c, g, p);
                a && b && (n.push("M", "circle" === f ? e + g / 2 : n[1] + n[4] / 2, c > b ? c: c + p, "L", a, b), n = n.concat(k.circle(a - 1, b - 1, 2, 2)));
                return n
            }
        }
        var E = m.addEvent,
        J = m.defined,
        F = m.isNumber,
        C = m.merge,
        B = m.objectEach,
        L = m.seriesType,
        z = m.wrap;
        m = f.noop;
        var x = f.Renderer,
        A = f.Series,
        t = f.TrackerMixin,
        n = f.VMLRenderer,
        k = f.SVGRenderer.prototype.symbols;
        L("flags", "column", {
            pointRange: 0,
            allowOverlapX: !1,
            shape: "flag",
            stackDistance: 12,
            textAlign: "center",
            tooltip: {
                pointFormat: "{point.text}<br/>"
            },
            threshold: null,
            y: -30,
            fillColor: "#ffffff",
            lineWidth: 1,
            states: {
                hover: {
                    lineColor: "#000000",
                    fillColor: "#ccd6eb"
                }
            },
            style: {
                fontSize: "11px",
                fontWeight: "bold"
            }
        },
        {
            sorted: !1,
            noSharedTooltip: !0,
            allowDG: !1,
            takeOrdinalPosition: !1,
            trackerGroups: ["markerGroup"],
            forceCrop: !0,
            init: A.prototype.init,
            pointAttribs: function(f, e) {
                var c = this.options,
                g = f && f.color || this.color,
                k = c.lineColor,
                b = f && f.lineWidth;
                f = f && f.fillColor || c.fillColor;
                e && (f = c.states[e].fillColor, k = c.states[e].lineColor, b = c.states[e].lineWidth);
                return {
                    fill: f || g,
                    stroke: k || g,
                    "stroke-width": b || c.lineWidth || 0
                }
            },
            translate: y.translate,
            getPlotBox: y.getPlotBox,
            drawPoints: function() {
                var k = this.points,
                e = this.chart,
                c = e.renderer,
                g = e.inverted,
                p = this.options,
                b = p.y,
                a, n = this.yAxis,
                m = {},
                w = [];
                for (a = k.length; a--;) {
                    var l = k[a];
                    var h = (g ? l.plotY: l.plotX) > this.xAxis.len;
                    var u = l.plotX;
                    var r = l.stackIndex;
                    var t = l.options.shape || p.shape;
                    var x = l.plotY;
                    "undefined" !== typeof x && (x = l.plotY + b - ("undefined" !== typeof r && r * p.stackDistance));
                    l.anchorX = r ? void 0 : l.plotX;
                    var y = r ? void 0 : l.plotY;
                    var d = "flag" !== t;
                    r = l.graphic;
                    "undefined" !== typeof x && 0 <= u && !h ? (r || (r = l.graphic = c.label("", null, null, t, null, null, p.useHTML), e.styledMode || r.attr(this.pointAttribs(l)).css(C(p.style, l.style)), r.attr({
                        align: d ? "center": "left",
                        width: p.width,
                        height: p.height,
                        "text-align": p.textAlign
                    }).addClass("highcharts-point").add(this.markerGroup), l.graphic.div && (l.graphic.div.point = l), e.styledMode || r.shadow(p.shadow), r.isNew = !0), 0 < u && (u -= r.strokeWidth() % 2), t = {
                        y: x,
                        anchorY: y
                    },
                    p.allowOverlapX && (t.x = u, t.anchorX = l.anchorX), r.attr({
                        text: l.options.title || p.title || "A"
                    })[r.isNew ? "attr": "animate"](t), p.allowOverlapX || (m[l.plotX] ? m[l.plotX].size = Math.max(m[l.plotX].size, r.width) : m[l.plotX] = {
                        align: d ? .5 : 0,
                        size: r.width,
                        target: u,
                        anchorX: u
                    }), l.tooltipPos = [u, x + n.pos - e.plotTop]) : r && (l.graphic = r.destroy())
                }
                p.allowOverlapX || (B(m,
                function(a) {
                    a.plotX = a.anchorX;
                    w.push(a)
                }), f.distribute(w, g ? n.len: this.xAxis.len, 100), k.forEach(function(a) {
                    var b = a.graphic && m[a.plotX];
                    b && (a.graphic[a.graphic.isNew ? "attr": "animate"]({
                        x: b.pos + b.align * b.size,
                        anchorX: a.anchorX
                    }), J(b.pos) ? a.graphic.isNew = !1 : (a.graphic.attr({
                        x: -9999,
                        anchorX: -9999
                    }), a.graphic.isNew = !0))
                }));
                p.useHTML && z(this.markerGroup, "on",
                function(a) {
                    return f.SVGElement.prototype.on.apply(a.apply(this, [].slice.call(arguments, 1)), [].slice.call(arguments, 1))
                })
            },
            drawTracker: function() {
                var f = this.points;
                t.drawTrackerPoint.apply(this);
                f.forEach(function(e) {
                    var c = e.graphic;
                    c && E(c.element, "mouseover",
                    function() {
                        0 < e.stackIndex && !e.raised && (e._y = c.y, c.attr({
                            y: e._y - 8
                        }), e.raised = !0);
                        f.forEach(function(c) {
                            c !== e && c.raised && c.graphic && (c.graphic.attr({
                                y: c._y
                            }), c.raised = !1)
                        })
                    })
                })
            },
            animate: function(f) {
                f && this.setClip()
            },
            setClip: function() {
                A.prototype.setClip.apply(this, arguments); ! 1 !== this.options.clip && this.sharedClipKey && this.markerGroup.clip(this.chart[this.sharedClipKey])
            },
            buildKDTree: m,
            invertGroups: m
        },
        {
            isValid: function() {
                return F(this.y) || "undefined" === typeof this.y
            }
        });
        k.flag = function(f, e, c, g, p) {
            var b = p && p.anchorX || f;
            p = p && p.anchorY || e;
            return k.circle(b - 1, p - 1, 2, 2).concat(["M", b, p, "L", f, e + g, f, e, f + c, e, f + c, e + g, f, e + g, "Z"])
        };
        r("circle");
        r("square");
        x === n && ["circlepin", "flag", "squarepin"].forEach(function(f) {
            n.prototype.symbols[f] = k[f]
        });
        ""
    });
    P(y, "parts/RangeSelector.js", [y["parts/Globals.js"], y["parts/Utilities.js"]],
    function(f, m) {
        function y(c) {
            this.init(c)
        }
        var r = m.addEvent,
        E = m.createElement,
        N = m.css,
        F = m.defined,
        C = m.destroyObjectProperties,
        B = m.discardElement,
        L = m.extend,
        z = m.fireEvent,
        x = m.isNumber,
        A = m.merge,
        t = m.objectEach,
        n = m.pick,
        k = m.pInt,
        q = m.splat,
        e = f.Axis;
        m = f.Chart;
        var c = f.defaultOptions;
        L(c, {
            rangeSelector: {
                verticalAlign: "top",
                buttonTheme: {
                    width: 28,
                    height: 18,
                    padding: 2,
                    zIndex: 7
                },
                floating: !1,
                x: 0,
                y: 0,
                height: void 0,
                inputPosition: {
                    align: "right",
                    x: 0,
                    y: 0
                },
                buttonPosition: {
                    align: "left",
                    x: 0,
                    y: 0
                },
                labelStyle: {
                    color: "#666666"
                }
            }
        });
        c.lang = A(c.lang, {
            rangeSelectorZoom: "Zoom",
            rangeSelectorFrom: "From",
            rangeSelectorTo: "To"
        });
        y.prototype = {
            clickButton: function(c, f) {
                var b = this.chart,
                a = this.buttonOptions[c],
                g = b.xAxis[0],
                k = b.scroller && b.scroller.getUnionExtremes() || g || {},
                p = k.dataMin,
                l = k.dataMax,
                h = g && Math.round(Math.min(g.max, n(l, g.max))),
                m = a.type;
                k = a._range;
                var t, y = a.dataGrouping;
                if (null !== p && null !== l) {
                    b.fixedRange = k;
                    y && (this.forcedDataGrouping = !0, e.prototype.setDataGrouping.call(g || {
                        chart: this.chart
                    },
                    y, !1), this.frozenStates = a.preserveDataGrouping);
                    if ("month" === m || "year" === m) if (g) {
                        m = {
                            range: a,
                            max: h,
                            chart: b,
                            dataMin: p,
                            dataMax: l
                        };
                        var z = g.minFromRange.call(m);
                        x(m.newMax) && (h = m.newMax)
                    } else k = a;
                    else if (k) z = Math.max(h - k, p),
                    h = Math.min(z + k, l);
                    else if ("ytd" === m) if (g)"undefined" === typeof l && (p = Number.MAX_VALUE, l = Number.MIN_VALUE, b.series.forEach(function(a) {
                        a = a.xData;
                        p = Math.min(a[0], p);
                        l = Math.max(a[a.length - 1], l)
                    }), f = !1),
                    h = this.getYTDExtremes(l, p, b.time.useUTC),
                    z = t = h.min,
                    h = h.max;
                    else {
                        this.deferredYTDClick = c;
                        return
                    } else "all" === m && g && (z = p, h = l);
                    z += a._offsetMin;
                    h += a._offsetMax;
                    this.setSelected(c);
                    if (g) g.setExtremes(z, h, n(f, 1), null, {
                        trigger: "rangeSelectorButton",
                        rangeSelectorButton: a
                    });
                    else {
                        var A = q(b.options.xAxis)[0];
                        var d = A.range;
                        A.range = k;
                        var B = A.min;
                        A.min = t;
                        r(b, "load",
                        function() {
                            A.range = d;
                            A.min = B
                        })
                    }
                }
            },
            setSelected: function(c) {
                this.selected = this.options.selected = c
            },
            defaultButtons: [{
                type: "month",
                count: 1,
                text: "1m"
            },
            {
                type: "month",
                count: 3,
                text: "3m"
            },
            {
                type: "month",
                count: 6,
                text: "6m"
            },
            {
                type: "ytd",
                text: "YTD"
            },
            {
                type: "year",
                count: 1,
                text: "1y"
            },
            {
                type: "all",
                text: "All"
            }],
            init: function(c) {
                var e = this,
                b = c.options.rangeSelector,
                a = b.buttons || [].concat(e.defaultButtons),
                f = b.selected,
                g = function() {
                    var a = e.minInput,
                    b = e.maxInput;
                    a && a.blur && z(a, "blur");
                    b && b.blur && z(b, "blur")
                };
                e.chart = c;
                e.options = b;
                e.buttons = [];
                e.buttonOptions = a;
                this.unMouseDown = r(c.container, "mousedown", g);
                this.unResize = r(c, "resize", g);
                a.forEach(e.computeButtonRange);
                "undefined" !== typeof f && a[f] && this.clickButton(f, !1);
                r(c, "load",
                function() {
                    c.xAxis && c.xAxis[0] && r(c.xAxis[0], "setExtremes",
                    function(a) {
                        this.max - this.min !== c.fixedRange && "rangeSelectorButton" !== a.trigger && "updatedData" !== a.trigger && e.forcedDataGrouping && !e.frozenStates && this.setDataGrouping(!1, !1)
                    })
                })
            },
            updateButtonStates: function() {
                var c = this,
                e = this.chart,
                b = e.xAxis[0],
                a = Math.round(b.max - b.min),
                f = !b.hasVisibleSeries,
                k = e.scroller && e.scroller.getUnionExtremes() || b,
                n = k.dataMin,
                l = k.dataMax;
                e = c.getYTDExtremes(l, n, e.time.useUTC);
                var h = e.min,
                m = e.max,
                q = c.selected,
                r = x(q),
                t = c.options.allButtonsEnabled,
                y = c.buttons;
                c.buttonOptions.forEach(function(d, e) {
                    var g = d._range,
                    k = d.type,
                    p = d.count || 1,
                    w = y[e],
                    u = 0,
                    v = d._offsetMax - d._offsetMin;
                    d = e === q;
                    var x = g > l - n,
                    z = g < b.minRange,
                    A = !1,
                    D = !1;
                    g = g === a; ("month" === k || "year" === k) && a + 36E5 >= 864E5 * {
                        month: 28,
                        year: 365
                    } [k] * p - v && a - 36E5 <= 864E5 * {
                        month: 31,
                        year: 366
                    } [k] * p + v ? g = !0 : "ytd" === k ? (g = m - h + v === a, A = !d) : "all" === k && (g = b.max - b.min >= l - n, D = !d && r && g);
                    k = !t && (x || z || D || f);
                    p = d && g || g && !r && !A || d && c.frozenStates;
                    k ? u = 3 : p && (r = !0, u = 2);
                    w.state !== u && (w.setState(u), 0 === u && q === e && c.setSelected(null))
                })
            },
            computeButtonRange: function(c) {
                var e = c.type,
                b = c.count || 1,
                a = {
                    millisecond: 1,
                    second: 1E3,
                    minute: 6E4,
                    hour: 36E5,
                    day: 864E5,
                    week: 6048E5
                };
                if (a[e]) c._range = a[e] * b;
                else if ("month" === e || "year" === e) c._range = 864E5 * {
                    month: 30,
                    year: 365
                } [e] * b;
                c._offsetMin = n(c.offsetMin, 0);
                c._offsetMax = n(c.offsetMax, 0);
                c._range += c._offsetMax - c._offsetMin
            },
            setInputValue: function(c, e) {
                var b = this.chart.options.rangeSelector,
                a = this.chart.time,
                f = this[c + "Input"];
                F(e) && (f.previousValue = f.HCTime, f.HCTime = e);
                f.value = a.dateFormat(b.inputEditDateFormat || "%Y-%m-%d", f.HCTime);
                this[c + "DateBox"].attr({
                    text: a.dateFormat(b.inputDateFormat || "%b %e, %Y", f.HCTime)
                })
            },
            showInput: function(c) {
                var e = this.inputGroup,
                b = this[c + "DateBox"];
                N(this[c + "Input"], {
                    left: e.translateX + b.x + "px",
                    top: e.translateY + "px",
                    width: b.width - 2 + "px",
                    height: b.height - 2 + "px",
                    border: "2px solid silver"
                })
            },
            hideInput: function(c) {
                N(this[c + "Input"], {
                    border: 0,
                    width: "1px",
                    height: "1px"
                });
                this.setInputValue(c)
            },
            drawInput: function(e) {
                function g() {
                    var c = u.value,
                    e = (q.inputDateParser || Date.parse)(c),
                    d = a.xAxis[0],
                    f = a.scroller && a.scroller.xAxis ? a.scroller.xAxis: d,
                    g = f.dataMin;
                    f = f.dataMax;
                    e !== u.previousValue && (u.previousValue = e, x(e) || (e = c.split("-"), e = Date.UTC(k(e[0]), k(e[1]) - 1, k(e[2]))), x(e) && (a.time.useUTC || (e += 6E4 * (new Date).getTimezoneOffset()), h ? e > b.maxInput.HCTime ? e = void 0 : e < g && (e = g) : e < b.minInput.HCTime ? e = void 0 : e > f && (e = f), "undefined" !== typeof e && d.setExtremes(h ? e: d.min, h ? d.max: e, void 0, void 0, {
                        trigger: "rangeSelectorInput"
                    })))
                }
                var b = this,
                a = b.chart,
                n = a.renderer.style || {},
                m = a.renderer,
                q = a.options.rangeSelector,
                l = b.div,
                h = "min" === e,
                u, r, t = this.inputGroup;
                this[e + "Label"] = r = m.label(c.lang[h ? "rangeSelectorFrom": "rangeSelectorTo"], this.inputGroup.offset).addClass("highcharts-range-label").attr({
                    padding: 2
                }).add(t);
                t.offset += r.width + 5;
                this[e + "DateBox"] = m = m.label("", t.offset).addClass("highcharts-range-input").attr({
                    padding: 2,
                    width: q.inputBoxWidth || 90,
                    height: q.inputBoxHeight || 17,
                    "text-align": "center"
                }).on("click",
                function() {
                    b.showInput(e);
                    b[e + "Input"].focus()
                });
                a.styledMode || m.attr({
                    stroke: q.inputBoxBorderColor || "#cccccc",
                    "stroke-width": 1
                });
                m.add(t);
                t.offset += m.width + (h ? 10 : 0);
                this[e + "Input"] = u = E("input", {
                    name: e,
                    className: "highcharts-range-selector",
                    type: "text"
                },
                {
                    top: a.plotTop + "px"
                },
                l);
                a.styledMode || (r.css(A(n, q.labelStyle)), m.css(A({
                    color: "#333333"
                },
                n, q.inputStyle)), N(u, L({
                    position: "absolute",
                    border: 0,
                    width: "1px",
                    height: "1px",
                    padding: 0,
                    textAlign: "center",
                    fontSize: n.fontSize,
                    fontFamily: n.fontFamily,
                    top: "-9999em"
                },
                q.inputStyle)));
                u.onfocus = function() {
                    b.showInput(e)
                };
                u.onblur = function() {
                    u === f.doc.activeElement && g();
                    b.hideInput(e);
                    u.blur()
                };
                u.onchange = g;
                u.onkeypress = function(a) {
                    13 === a.keyCode && g()
                }
            },
            getPosition: function() {
                var c = this.chart,
                e = c.options.rangeSelector;
                c = "top" === e.verticalAlign ? c.plotTop - c.axisOffset[0] : 0;
                return {
                    buttonTop: c + e.buttonPosition.y,
                    inputTop: c + e.inputPosition.y - 10
                }
            },
            getYTDExtremes: function(c, e, b) {
                var a = this.chart.time,
                f = new a.Date(c),
                g = a.get("FullYear", f);
                b = b ? a.Date.UTC(g, 0, 1) : +new a.Date(g, 0, 1);
                e = Math.max(e || 0, b);
                f = f.getTime();
                return {
                    max: Math.min(c || f, f),
                    min: e
                }
            },
            render: function(e, f) {
                var b = this,
                a = b.chart,
                g = a.renderer,
                k = a.container,
                m = a.options,
                l = m.exporting && !1 !== m.exporting.enabled && m.navigation && m.navigation.buttonOptions,
                h = c.lang,
                p = b.div,
                q = m.rangeSelector,
                r = n(m.chart.style && m.chart.style.zIndex, 0) + 1;
                m = q.floating;
                var t = b.buttons;
                p = b.inputGroup;
                var x = q.buttonTheme,
                d = q.buttonPosition,
                y = q.inputPosition,
                z = q.inputEnabled,
                A = x && x.states,
                B = a.plotLeft,
                C = b.buttonGroup,
                F, J = b.options.verticalAlign,
                L = a.legend,
                N = L && L.options,
                P = d.y,
                S = y.y,
                aa = a.hasLoaded,
                ca = aa ? "animate": "attr",
                Y = 0,
                U = 0;
                if (!1 !== q.enabled) {
                    b.rendered || (b.group = F = g.g("range-selector-group").attr({
                        zIndex: 7
                    }).add(), b.buttonGroup = C = g.g("range-selector-buttons").add(F), b.zoomText = g.text(h.rangeSelectorZoom, 0, 15).add(C), a.styledMode || (b.zoomText.css(q.labelStyle), x["stroke-width"] = n(x["stroke-width"], 0)), b.buttonOptions.forEach(function(a, c) {
                        t[c] = g.button(a.text, 0, 0,
                        function(d) {
                            var e = a.events && a.events.click,
                            f;
                            e && (f = e.call(a, d)); ! 1 !== f && b.clickButton(c);
                            b.isActive = !0
                        },
                        x, A && A.hover, A && A.select, A && A.disabled).attr({
                            "text-align": "center"
                        }).add(C)
                    }), !1 !== z && (b.div = p = E("div", null, {
                        position: "relative",
                        height: 0,
                        zIndex: r
                    }), k.parentNode.insertBefore(p, k), b.inputGroup = p = g.g("input-group").add(F), p.offset = 0, b.drawInput("min"), b.drawInput("max")));
                    b.zoomText[ca]({
                        x: n(B + d.x, B)
                    });
                    var da = n(B + d.x, B) + b.zoomText.getBBox().width + 5;
                    b.buttonOptions.forEach(function(a, b) {
                        t[b][ca]({
                            x: da
                        });
                        da += t[b].width + n(q.buttonSpacing, 5)
                    });
                    B = a.plotLeft - a.spacing[3];
                    b.updateButtonStates();
                    l && this.titleCollision(a) && "top" === J && "right" === d.align && d.y + C.getBBox().height - 12 < (l.y || 0) + l.height && (Y = -40);
                    k = d.x - a.spacing[3];
                    "right" === d.align ? k += Y - B: "center" === d.align && (k -= B / 2);
                    C.align({
                        y: d.y,
                        width: C.getBBox().width,
                        align: d.align,
                        x: k
                    },
                    !0, a.spacingBox);
                    b.group.placed = aa;
                    b.buttonGroup.placed = aa; ! 1 !== z && (Y = l && this.titleCollision(a) && "top" === J && "right" === y.align && y.y - p.getBBox().height - 12 < (l.y || 0) + l.height + a.spacing[0] ? -40 : 0, "left" === y.align ? k = B: "right" === y.align && (k = -Math.max(a.axisOffset[1], -Y)), p.align({
                        y: y.y,
                        width: p.getBBox().width,
                        align: y.align,
                        x: y.x + k - 2
                    },
                    !0, a.spacingBox), l = p.alignAttr.translateX + p.alignOptions.x - Y + p.getBBox().x + 2, k = p.alignOptions.width, h = C.alignAttr.translateX + C.getBBox().x, B = C.getBBox().width + 20, (y.align === d.align || h + B > l && l + k > h && P < S + p.getBBox().height) && p.attr({
                        translateX: p.alignAttr.translateX + (a.axisOffset[1] >= -Y ? 0 : -Y),
                        translateY: p.alignAttr.translateY + C.getBBox().height + 10
                    }), b.setInputValue("min", e), b.setInputValue("max", f), b.inputGroup.placed = aa);
                    b.group.align({
                        verticalAlign: J
                    },
                    !0, a.spacingBox);
                    e = b.group.getBBox().height + 20;
                    f = b.group.alignAttr.translateY;
                    "bottom" === J && (L = N && "bottom" === N.verticalAlign && N.enabled && !N.floating ? L.legendHeight + n(N.margin, 10) : 0, e = e + L - 20, U = f - e - (m ? 0 : q.y) - (a.titleOffset ? a.titleOffset[2] : 0) - 10);
                    if ("top" === J) m && (U = 0),
                    a.titleOffset && a.titleOffset[0] && (U = a.titleOffset[0]),
                    U += a.margin[0] - a.spacing[0] || 0;
                    else if ("middle" === J) if (S === P) U = 0 > S ? f + void 0 : f;
                    else if (S || P) U = 0 > S || 0 > P ? U - Math.min(S, P) : f - e + NaN;
                    b.group.translate(q.x, q.y + Math.floor(U)); ! 1 !== z && (b.minInput.style.marginTop = b.group.translateY + "px", b.maxInput.style.marginTop = b.group.translateY + "px");
                    b.rendered = !0
                }
            },
            getHeight: function() {
                var c = this.options,
                e = this.group,
                b = c.y,
                a = c.buttonPosition.y,
                f = c.inputPosition.y;
                if (c.height) return c.height;
                c = e ? e.getBBox(!0).height + 13 + b: 0;
                e = Math.min(f, a);
                if (0 > f && 0 > a || 0 < f && 0 < a) c += Math.abs(e);
                return c
            },
            titleCollision: function(c) {
                return ! (c.options.title.text || c.options.subtitle.text)
            },
            update: function(c) {
                var e = this.chart;
                A(!0, e.options.rangeSelector, c);
                this.destroy();
                this.init(e);
                e.rangeSelector.render()
            },
            destroy: function() {
                var c = this,
                e = c.minInput,
                b = c.maxInput;
                c.unMouseDown();
                c.unResize();
                C(c.buttons);
                e && (e.onfocus = e.onblur = e.onchange = null);
                b && (b.onfocus = b.onblur = b.onchange = null);
                t(c,
                function(a, b) {
                    a && "chart" !== b && (a.destroy ? a.destroy() : a.nodeType && B(this[b]));
                    a !== y.prototype[b] && (c[b] = null)
                },
                this)
            }
        };
        e.prototype.minFromRange = function() {
            var c = this.range,
            e = c.type,
            b = this.max,
            a = this.chart.time,
            f = function(b, c) {
                var f = "year" === e ? "FullYear": "Month",
                g = new a.Date(b),
                h = a.get(f, g);
                a.set(f, g, h + c);
                h === a.get(f, g) && a.set("Date", g, 0);
                return g.getTime() - b
            };
            if (x(c)) {
                var k = b - c;
                var m = c
            } else k = b + f(b, -c.count),
            this.chart && (this.chart.fixedRange = b - k);
            var l = n(this.dataMin, Number.MIN_VALUE);
            x(k) || (k = l);
            k <= l && (k = l, "undefined" === typeof m && (m = f(k, c.count)), this.newMax = Math.min(k + m, this.dataMax));
            x(b) || (k = void 0);
            return k
        };
        f.RangeSelector || (r(m, "afterGetContainer",
        function() {
            this.options.rangeSelector.enabled && (this.rangeSelector = new y(this))
        }), r(m, "beforeRender",
        function() {
            var c = this.axes,
            e = this.rangeSelector;
            e && (x(e.deferredYTDClick) && (e.clickButton(e.deferredYTDClick), delete e.deferredYTDClick), c.forEach(function(b) {
                b.updateNames();
                b.setScale()
            }), this.getAxisMargins(), e.render(), c = e.options.verticalAlign, e.options.floating || ("bottom" === c ? this.extraBottomMargin = !0 : "middle" !== c && (this.extraTopMargin = !0)))
        }), r(m, "update",
        function(c) {
            var e = c.options.rangeSelector;
            c = this.rangeSelector;
            var b = this.extraBottomMargin,
            a = this.extraTopMargin;
            e && e.enabled && !F(c) && (this.options.rangeSelector.enabled = !0, this.rangeSelector = new y(this));
            this.extraTopMargin = this.extraBottomMargin = !1;
            c && (c.render(), e = e && e.verticalAlign || c.options && c.options.verticalAlign, c.options.floating || ("bottom" === e ? this.extraBottomMargin = !0 : "middle" !== e && (this.extraTopMargin = !0)), this.extraBottomMargin !== b || this.extraTopMargin !== a) && (this.isDirtyBox = !0)
        }), r(m, "render",
        function() {
            var c = this.rangeSelector;
            c && !c.options.floating && (c.render(), c = c.options.verticalAlign, "bottom" === c ? this.extraBottomMargin = !0 : "middle" !== c && (this.extraTopMargin = !0))
        }), r(m, "getMargins",
        function() {
            var c = this.rangeSelector;
            c && (c = c.getHeight(), this.extraTopMargin && (this.plotTop += c), this.extraBottomMargin && (this.marginBottom += c))
        }), m.prototype.callbacks.push(function(c) {
            function e() {
                b = c.xAxis[0].getExtremes();
                x(b.min) && a.render(b.min, b.max)
            }
            var b, a = c.rangeSelector;
            if (a) {
                var f = r(c.xAxis[0], "afterSetExtremes",
                function(b) {
                    a.render(b.min, b.max)
                });
                var g = r(c, "redraw", e);
                e()
            }
            r(c, "destroy",
            function() {
                a && (g(), f())
            })
        }), f.RangeSelector = y)
    });
    P(y, "parts/StockChart.js", [y["parts/Globals.js"], y["parts/Point.js"], y["parts/Utilities.js"]],
    function(f, m, y) {
        var r = y.addEvent,
        E = y.arrayMax,
        J = y.arrayMin,
        F = y.clamp,
        C = y.defined,
        B = y.extend,
        L = y.find,
        z = y.format,
        x = y.isNumber,
        A = y.isString,
        t = y.merge,
        n = y.pick,
        k = y.splat;
        y = f.Axis;
        var q = f.Chart,
        e = f.Renderer,
        c = f.Series,
        g = f.SVGRenderer,
        p = f.VMLRenderer,
        b = c.prototype,
        a = b.init,
        v = b.processData,
        D = m.prototype.tooltipFormatter;
        f.StockChart = f.stockChart = function(a, b, c) {
            var e = A(a) || a.nodeName,
            g = arguments[e ? 1 : 0],
            h = g,
            l = g.series,
            m = f.getOptions(),
            d,
            p = g.chart && g.chart.panning,
            r = n(g.navigator && g.navigator.enabled, m.navigator.enabled, !0),
            w = p && /y/.test(p.type),
            v = {
                startOnTick: !1,
                endOnTick: !1
            };
            g.xAxis = k(g.xAxis || {}).map(function(a, b) {
                return t({
                    minPadding: 0,
                    maxPadding: 0,
                    overscroll: 0,
                    ordinal: !0,
                    title: {
                        text: null
                    },
                    labels: {
                        overflow: "justify"
                    },
                    showLastLabel: !0
                },
                m.xAxis, m.xAxis && m.xAxis[b], a, {
                    type: "datetime",
                    categories: null
                },
                r ? v: null)
            });
            g.yAxis = k(g.yAxis || {}).map(function(a, b) {
                d = n(a.opposite, !0);
                return t({
                    labels: {
                        y: -2
                    },
                    opposite: d,
                    showLastLabel: !(!a.categories && "category" !== a.type),
                    title: {
                        text: null
                    }
                },
                m.yAxis, m.yAxis && m.yAxis[b], a, w ? v: null)
            });
            g.series = null;
            g = t({
                chart: {
                    panning: {
                        enabled: !0,
                        type: "x"
                    },
                    pinchType: "x"
                },
                navigator: {
                    enabled: r
                },
                scrollbar: {
                    enabled: n(m.scrollbar.enabled, !0)
                },
                rangeSelector: {
                    enabled: n(m.rangeSelector.enabled, !0)
                },
                title: {
                    text: null
                },
                tooltip: {
                    split: n(m.tooltip.split, !0),
                    crosshairs: !0
                },
                legend: {
                    enabled: !1
                }
            },
            g, {
                isStock: !0
            });
            g.series = h.series = l;
            return e ? new q(a, g, c) : new q(g, b)
        };
        r(c, "setOptions",
        function(a) {
            var b;
            this.chart.options.isStock && (this.is("column") || this.is("columnrange") ? b = {
                borderWidth: 0,
                shadow: !1
            }: this.is("scatter") || this.is("sma") || (b = {
                marker: {
                    enabled: !1,
                    radius: 2
                }
            }), b && (a.plotOptions[this.type] = t(a.plotOptions[this.type], b)))
        });
        r(y, "autoLabelAlign",
        function(a) {
            var b = this.chart,
            c = this.options;
            b = b._labelPanes = b._labelPanes || {};
            var e = this.options.labels;
            this.chart.options.isStock && "yAxis" === this.coll && (c = c.top + "," + c.height, !b[c] && e.enabled && (15 === e.x && (e.x = 0), "undefined" === typeof e.align && (e.align = "right"), b[c] = this, a.align = "right", a.preventDefault()))
        });
        r(y, "destroy",
        function() {
            var a = this.chart,
            b = this.options && this.options.top + "," + this.options.height;
            b && a._labelPanes && a._labelPanes[b] === this && delete a._labelPanes[b]
        });
        r(y, "getPlotLinePath",
        function(a) {
            function b(a) {
                var b = "xAxis" === a ? "yAxis": "xAxis";
                a = c.options[b];
                return x(a) ? [f[b][a]] : A(a) ? [f.get(a)] : e.map(function(a) {
                    return a[b]
                })
            }
            var c = this,
            e = this.isLinked && !this.series ? this.linkedParent.series: this.series,
            f = c.chart,
            g = f.renderer,
            k = c.left,
            m = c.top,
            d,
            p,
            q,
            r,
            w = [],
            t = [],
            v = a.translatedValue,
            y = a.value,
            z = a.force;
            if (f.options.isStock && !1 !== a.acrossPanes && "xAxis" === c.coll || "yAxis" === c.coll) {
                a.preventDefault();
                t = b(c.coll);
                var B = c.isXAxis ? f.yAxis: f.xAxis;
                B.forEach(function(a) {
                    if (C(a.options.id) ? -1 === a.options.id.indexOf("navigator") : 1) {
                        var b = a.isXAxis ? "yAxis": "xAxis";
                        b = C(a.options[b]) ? f[b][a.options[b]] : f[b][0];
                        c === b && t.push(a)
                    }
                });
                var D = t.length ? [] : [c.isXAxis ? f.yAxis[0] : f.xAxis[0]];
                t.forEach(function(a) { - 1 !== D.indexOf(a) || L(D,
                    function(b) {
                        return b.pos === a.pos && b.len === a.len
                    }) || D.push(a)
                });
                var E = n(v, c.translate(y, null, null, a.old));
                x(E) && (c.horiz ? D.forEach(function(a) {
                    var b;
                    p = a.pos;
                    r = p + a.len;
                    d = q = Math.round(E + c.transB);
                    "pass" !== z && (d < k || d > k + c.width) && (z ? d = q = F(d, k, k + c.width) : b = !0);
                    b || w.push("M", d, p, "L", q, r)
                }) : D.forEach(function(a) {
                    var b;
                    d = a.pos;
                    q = d + a.len;
                    p = r = Math.round(m + c.height - E);
                    "pass" !== z && (p < m || p > m + c.height) && (z ? p = r = F(p, m, m + c.height) : b = !0);
                    b || w.push("M", d, p, "L", q, r)
                }));
                a.path = 0 < w.length ? g.crispPolyLine(w, a.lineWidth || 1) : null
            }
        });
        g.prototype.crispPolyLine = function(a, b) {
            var c;
            for (c = 0; c < a.length; c += 6) a[c + 1] === a[c + 4] && (a[c + 1] = a[c + 4] = Math.round(a[c + 1]) - b % 2 / 2),
            a[c + 2] === a[c + 5] && (a[c + 2] = a[c + 5] = Math.round(a[c + 2]) + b % 2 / 2);
            return a
        };
        e === p && (p.prototype.crispPolyLine = g.prototype.crispPolyLine);
        r(y, "afterHideCrosshair",
        function() {
            this.crossLabel && (this.crossLabel = this.crossLabel.hide())
        });
        r(y, "afterDrawCrosshair",
        function(a) {
            var b, c;
            if (C(this.crosshair.label) && this.crosshair.label.enabled && this.cross) {
                var e = this.chart,
                f = this.options.crosshair.label,
                g = this.horiz,
                k = this.opposite,
                m = this.left,
                d = this.top,
                p = this.crossLabel,
                q = f.format,
                r = "",
                t = "inside" === this.options.tickPosition,
                w = !1 !== this.crosshair.snap,
                v = 0,
                x = a.e || this.cross && this.cross.e,
                y = a.point;
                var A = this.lin2log;
                if (this.isLog) {
                    a = A(this.min);
                    var D = A(this.max)
                } else a = this.min,
                D = this.max;
                A = g ? "center": k ? "right" === this.labelAlign ? "right": "left": "left" === this.labelAlign ? "left": "center";
                p || (p = this.crossLabel = e.renderer.label(null, null, null, f.shape || "callout").addClass("highcharts-crosshair-label" + (this.series[0] && " highcharts-color-" + this.series[0].colorIndex)).attr({
                    align: f.align || A,
                    padding: n(f.padding, 8),
                    r: n(f.borderRadius, 3),
                    zIndex: 2
                }).add(this.labelGroup), e.styledMode || p.attr({
                    fill: f.backgroundColor || this.series[0] && this.series[0].color || "#666666",
                    stroke: f.borderColor || "",
                    "stroke-width": f.borderWidth || 0
                }).css(B({
                    color: "#ffffff",
                    fontWeight: "normal",
                    fontSize: "11px",
                    textAlign: "center"
                },
                f.style)));
                g ? (A = w ? y.plotX + m: x.chartX, d += k ? 0 : this.height) : (A = k ? this.width + m: 0, d = w ? y.plotY + d: x.chartY);
                q || f.formatter || (this.isDatetimeAxis && (r = "%b %d, %Y"), q = "{value" + (r ? ":" + r: "") + "}");
                r = w ? y[this.isXAxis ? "x": "y"] : this.toValue(g ? x.chartX: x.chartY);
                p.attr({
                    text: q ? z(q, {
                        value: r
                    },
                    e) : f.formatter.call(this, r),
                    x: A,
                    y: d,
                    visibility: r < a || r > D ? "hidden": "visible"
                });
                f = p.getBBox();
                if (g) {
                    if (t && !k || !t && k) d = p.y - f.height
                } else d = p.y - f.height / 2;
                g ? (b = m - f.x, c = m + this.width - f.x) : (b = "left" === this.labelAlign ? m: 0, c = "right" === this.labelAlign ? m + this.width: e.chartWidth);
                p.translateX < b && (v = b - p.translateX);
                p.translateX + f.width >= c && (v = -(p.translateX + f.width - c));
                p.attr({
                    x: A + v,
                    y: d,
                    anchorX: g ? A: this.opposite ? 0 : e.chartWidth,
                    anchorY: g ? this.opposite ? e.chartHeight: 0 : d + f.height / 2
                })
            }
        });
        b.init = function() {
            a.apply(this, arguments);
            this.setCompare(this.options.compare)
        };
        b.setCompare = function(a) {
            this.modifyValue = "value" === a || "percent" === a ?
            function(b, c) {
                var e = this.compareValue;
                return "undefined" !== typeof b && "undefined" !== typeof e ? (b = "value" === a ? b - e: b / e * 100 - (100 === this.options.compareBase ? 0 : 100), c && (c.change = b), b) : 0
            }: null;
            this.userOptions.compare = a;
            this.chart.hasRendered && (this.isDirty = !0)
        };
        b.processData = function(a) {
            var b, c = -1,
            e = !0 === this.options.compareStart ? 0 : 1;
            v.apply(this, arguments);
            if (this.xAxis && this.processedYData) {
                var f = this.processedXData;
                var g = this.processedYData;
                var k = g.length;
                this.pointArrayMap && (c = this.pointArrayMap.indexOf(this.options.pointValKey || this.pointValKey || "y"));
                for (b = 0; b < k - e; b++) {
                    var m = g[b] && -1 < c ? g[b][c] : g[b];
                    if (x(m) && f[b + e] >= this.xAxis.min && 0 !== m) {
                        this.compareValue = m;
                        break
                    }
                }
            }
        };
        r(c, "afterGetExtremes",
        function() {
            if (this.modifyValue) {
                var a = [this.modifyValue(this.dataMin), this.modifyValue(this.dataMax)];
                this.dataMin = J(a);
                this.dataMax = E(a)
            }
        });
        y.prototype.setCompare = function(a, b) {
            this.isXAxis || (this.series.forEach(function(b) {
                b.setCompare(a)
            }), n(b, !0) && this.chart.redraw())
        };
        m.prototype.tooltipFormatter = function(a) {
            var b = this.series.chart.numberFormatter;
            a = a.replace("{point.change}", (0 < this.change ? "+": "") + b(this.change, n(this.series.tooltipOptions.changeDecimals, 2)));
            return D.apply(this, [a])
        };
        r(c, "render",
        function() {
            var a = this.chart;
            if (! (a.is3d && a.is3d() || a.polar) && this.xAxis && !this.xAxis.isRadial) {
                var b = this.yAxis.len;
                if (this.xAxis.axisLine) {
                    var c = a.plotTop + a.plotHeight - this.yAxis.pos - this.yAxis.len,
                    e = Math.floor(this.xAxis.axisLine.strokeWidth() / 2);
                    0 <= c && (b -= Math.max(e - c, 0))
                } ! this.clipBox && this.animate ? (this.clipBox = t(a.clipBox), this.clipBox.width = this.xAxis.len, this.clipBox.height = b) : a[this.sharedClipKey] && (a[this.sharedClipKey].animate({
                    width: this.xAxis.len,
                    height: b
                }), a[this.sharedClipKey + "m"] && a[this.sharedClipKey + "m"].animate({
                    width: this.xAxis.len
                }))
            }
        });
        r(q, "update",
        function(a) {
            a = a.options;
            "scrollbar" in a && this.navigator && (t(!0, this.options.scrollbar, a.scrollbar), this.navigator.update({},
            !1), delete a.scrollbar)
        });
        r(y, "afterSetScale",
        function() {
            var a = this,
            b = a.chart.options.chart && a.chart.options.chart.panning;
            if (b && ("y" === b.type || "xy" === b.type) && !a.isXAxis && !C(a.panningState)) {
                var c = Number.MAX_VALUE,
                e = Number.MIN_VALUE;
                a.series.forEach(function(b) {
                    c = Math.min(f.arrayMin(b.yData), c) - (a.min && a.dataMin ? a.dataMin - a.min: 0);
                    e = Math.max(f.arrayMax(b.yData), e) + (a.max && a.dataMax ? a.max - a.dataMax: 0)
                });
                a.panningState = {
                    startMin: c,
                    startMax: e
                }
            }
        })
    });
    P(y, "masters/modules/stock.src.js", [],
    function() {});
    P(y, "masters/highstock.src.js", [y["masters/highcharts.src.js"]],
    function(f) {
        f.product = "Highstock";
        return f
    });
    y["masters/highstock.src.js"]._modules = y;
    return y["masters/highstock.src.js"]
});
//# sourceMappingURL=highstock.js.map