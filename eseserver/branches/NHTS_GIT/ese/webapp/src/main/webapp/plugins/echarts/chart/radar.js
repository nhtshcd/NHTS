define("echarts/chart/radar",["require","./base","zrender/shape/Polygon","../component/polar","../config","../util/ecData","zrender/tool/util","zrender/tool/color","../util/accMath","../chart"],function(e){function t(e,t,n,a,o){i.call(this,e,t,n,a,o),this.refresh(a)}var i=e("./base"),n=e("zrender/shape/Polygon");e("../component/polar");var a=e("../config");a.radar={zlevel:0,z:2,clickable:!0,legendHoverLink:!0,polarIndex:0,itemStyle:{normal:{label:{show:!1},lineStyle:{width:2,type:"solid"}},emphasis:{label:{show:!1}}},symbolSize:2};var o=e("../util/ecData"),r=e("zrender/tool/util"),s=e("zrender/tool/color");return t.prototype={type:a.CHART_TYPE_RADAR,_buildShape:function(){this.selectedMap={},this._symbol=this.option.symbolList,this._queryTarget,this._dropBoxList=[],this._radarDataCounter=0;for(var e,t=this.series,i=this.component.legend,n=0,o=t.length;o>n;n++)t[n].type===a.CHART_TYPE_RADAR&&(this.serie=this.reformOption(t[n]),this.legendHoverLink=t[n].legendHoverLink||this.legendHoverLink,e=this.serie.name||"",this.selectedMap[e]=i?i.isSelected(e):!0,this.selectedMap[e]&&(this._queryTarget=[this.serie,this.option],this.deepQuery(this._queryTarget,"calculable")&&this._addDropBox(n),this._buildSingleRadar(n),this.buildMark(n)));this.addShapeList()},_buildSingleRadar:function(e){for(var t,i,n,a,o=this.component.legend,r=this.serie.data,s=this.deepQuery(this._queryTarget,"calculable"),l=0;l<r.length;l++)n=r[l].name||"",this.selectedMap[n]=o?o.isSelected(n):!0,this.selectedMap[n]&&(o?(i=o.getColor(n),t=o.getItemShape(n),t&&(t.style.brushType=this.deepQuery([r[l],this.serie],"itemStyle.normal.areaStyle")?"both":"stroke",o.setItemShape(n,t))):i=this.zr.getColor(l),a=this._getPointList(this.serie.polarIndex,r[l]),this._addSymbol(a,i,l,e,this.serie.polarIndex),this._addDataShape(a,i,r[l],e,l,s),this._radarDataCounter++)},_getPointList:function(e,t){for(var i,n,a=[],o=this.component.polar,r=0,s=t.value.length;s>r;r++)n=this.getDataFromOption(t.value[r]),i="-"!=n?o.getVector(e,r,n):!1,i&&a.push(i);return a},_addSymbol:function(e,t,i,n,a){for(var r,s=this.series,l=this.component.polar,h=0,d=e.length;d>h;h++)r=this.getSymbolShape(this.deepMerge([s[n].data[i],s[n]]),n,s[n].data[i].value[h],h,l.getIndicatorText(a,h),e[h][0],e[h][1],this._symbol[this._radarDataCounter%this._symbol.length],t,"#fff","vertical"),r.zlevel=this.getZlevelBase(),r.z=this.getZBase()+1,o.set(r,"data",s[n].data[i]),o.set(r,"value",s[n].data[i].value),o.set(r,"dataIndex",i),o.set(r,"special",h),this.shapeList.push(r)},_addDataShape:function(e,t,i,a,r,l){var h=this.series,d=[i,this.serie],c=this.getItemStyleColor(this.deepQuery(d,"itemStyle.normal.color"),a,r,i),p=this.deepQuery(d,"itemStyle.normal.lineStyle.width"),m=this.deepQuery(d,"itemStyle.normal.lineStyle.type"),u=this.deepQuery(d,"itemStyle.normal.areaStyle.color"),g=this.deepQuery(d,"itemStyle.normal.areaStyle"),V={zlevel:this.getZlevelBase(),z:this.getZBase(),style:{pointList:e,brushType:g?"both":"stroke",color:u||c||("string"==typeof t?s.alpha(t,.5):t),strokeColor:c||t,lineWidth:p,lineType:m},highlightStyle:{brushType:this.deepQuery(d,"itemStyle.emphasis.areaStyle")||g?"both":"stroke",color:this.deepQuery(d,"itemStyle.emphasis.areaStyle.color")||u||c||("string"==typeof t?s.alpha(t,.5):t),strokeColor:this.getItemStyleColor(this.deepQuery(d,"itemStyle.emphasis.color"),a,r,i)||c||t,lineWidth:this.deepQuery(d,"itemStyle.emphasis.lineStyle.width")||p,lineType:this.deepQuery(d,"itemStyle.emphasis.lineStyle.type")||m}};o.pack(V,h[a],a,i,r,i.name,this.component.polar.getIndicator(h[a].polarIndex)),l&&(V.draggable=!0,this.setCalculable(V)),V=new n(V),this.shapeList.push(V)},_addDropBox:function(e){var t=this.series,i=this.deepQuery(this._queryTarget,"polarIndex");if(!this._dropBoxList[i]){var n=this.component.polar.getDropBox(i);n.zlevel=this.getZlevelBase(),n.z=this.getZBase(),this.setCalculable(n),o.pack(n,t,e,void 0,-1),this.shapeList.push(n),this._dropBoxList[i]=!0}},ondragend:function(e,t){var i=this.series;if(this.isDragend&&e.target){var n=e.target,a=o.get(n,"seriesIndex"),r=o.get(n,"dataIndex");this.component.legend&&this.component.legend.del(i[a].data[r].name),i[a].data.splice(r,1),t.dragOut=!0,t.needRefresh=!0,this.isDragend=!1}},ondrop:function(t,i){var n=this.series;if(this.isDrop&&t.target){var a,r,s=t.target,l=t.dragged,h=o.get(s,"seriesIndex"),d=o.get(s,"dataIndex"),c=this.component.legend;if(-1===d)a={value:o.get(l,"value"),name:o.get(l,"name")},n[h].data.push(a),c&&c.add(a.name,l.style.color||l.style.strokeColor);else{var p=e("../util/accMath");a=n[h].data[d],c&&c.del(a.name),a.name+=this.option.nameConnector+o.get(l,"name"),r=o.get(l,"value");for(var m=0;m<r.length;m++)a.value[m]=p.accAdd(a.value[m],r[m]);c&&c.add(a.name,l.style.color||l.style.strokeColor)}i.dragIn=i.dragIn||!0,this.isDrop=!1}},refresh:function(e){e&&(this.option=e,this.series=e.series),this.backupShapeList(),this._buildShape()}},r.inherits(t,i),e("../chart").define("radar",t),t}),define("echarts/component/polar",["require","./base","zrender/shape/Text","zrender/shape/Line","zrender/shape/Polygon","zrender/shape/Circle","zrender/shape/Ring","../config","zrender/tool/util","../util/coordinates","../util/accMath","../util/smartSteps","../component"],function(e){function t(e,t,n,a,o){i.call(this,e,t,n,a,o),this.refresh(a)}var i=e("./base"),n=e("zrender/shape/Text"),a=e("zrender/shape/Line"),o=e("zrender/shape/Polygon"),r=e("zrender/shape/Circle"),s=e("zrender/shape/Ring"),l=e("../config");l.polar={zlevel:0,z:0,center:["50%","50%"],radius:"75%",startAngle:90,boundaryGap:[0,0],splitNumber:5,name:{show:!0,textStyle:{color:"#333"}},axisLine:{show:!0,lineStyle:{color:"#ccc",width:1,type:"solid"}},axisLabel:{show:!1,textStyle:{color:"#333"}},splitArea:{show:!0,areaStyle:{color:["rgba(250,250,250,0.3)","rgba(200,200,200,0.3)"]}},splitLine:{show:!0,lineStyle:{width:1,color:"#ccc"}},type:"polygon"};var h=e("zrender/tool/util"),d=e("../util/coordinates");return t.prototype={type:l.COMPONENT_TYPE_POLAR,_buildShape:function(){for(var e=0;e<this.polar.length;e++)this._index=e,this.reformOption(this.polar[e]),this._queryTarget=[this.polar[e],this.option],this._createVector(e),this._buildSpiderWeb(e),this._buildText(e),this._adjustIndicatorValue(e),this._addAxisLabel(e);for(var e=0;e<this.shapeList.length;e++)this.zr.addShape(this.shapeList[e])},_createVector:function(e){for(var t,i=this.polar[e],n=this.deepQuery(this._queryTarget,"indicator"),a=n.length,o=i.startAngle,r=2*Math.PI/a,s=this._getRadius(),l=i.__ecIndicator=[],h=0;a>h;h++)t=d.polar2cartesian(s,o*Math.PI/180+r*h),l.push({vector:[t[1],-t[0]]})},_getRadius:function(){var e=this.polar[this._index];return this.parsePercent(e.radius,Math.min(this.zr.getWidth(),this.zr.getHeight())/2)},_buildSpiderWeb:function(e){var t=this.polar[e],i=t.__ecIndicator,n=t.splitArea,a=t.splitLine,o=this.getCenter(e),r=t.splitNumber,s=a.lineStyle.color,l=a.lineStyle.width,h=a.show,d=this.deepQuery(this._queryTarget,"axisLine");this._addArea(i,r,o,n,s,l,h),d.show&&this._addLine(i,o,d)},_addAxisLabel:function(t){for(var i,a,o,r,a,s,l,d,c,p,m=e("../util/accMath"),u=this.polar[t],g=this.deepQuery(this._queryTarget,"indicator"),V=u.__ecIndicator,y=this.deepQuery(this._queryTarget,"splitNumber"),U=this.getCenter(t),f=0;f<g.length;f++)if(i=this.deepQuery([g[f],u,this.option],"axisLabel"),i.show){var _=this.deepQuery([i,u,this.option],"textStyle"),b=this.deepQuery([i,u],"formatter");if(o={},o.textFont=this.getFont(_),o.color=_.color,o=h.merge(o,i),o.lineWidth=o.width,a=V[f].vector,s=V[f].value,d=f/g.length*2*Math.PI,c=i.offset||10,p=i.interval||0,!s)return;for(var x=1;y>=x;x+=p+1)r=h.merge({},o),l=m.accAdd(s.min,m.accMul(s.step,x)),l="function"==typeof b?b(l):"string"==typeof b?b.replace("{a}","{a0}").replace("{a0}",l):this.numAddCommas(l),r.text=l,r.x=x*a[0]/y+Math.cos(d)*c+U[0],r.y=x*a[1]/y+Math.sin(d)*c+U[1],this.shapeList.push(new n({zlevel:this.getZlevelBase(),z:this.getZBase(),style:r,draggable:!1,hoverable:!1}))}},_buildText:function(e){for(var t,i,a,o,r,s,l,h=this.polar[e],d=h.__ecIndicator,c=this.deepQuery(this._queryTarget,"indicator"),p=this.getCenter(e),m=0,u=0,g=0;g<c.length;g++)o=this.deepQuery([c[g],h,this.option],"name"),o.show&&(l=this.deepQuery([o,h,this.option],"textStyle"),i={},i.textFont=this.getFont(l),i.color=l.color,i.text="function"==typeof o.formatter?o.formatter.call(this.myChart,c[g].text,g):"string"==typeof o.formatter?o.formatter.replace("{value}",c[g].text):c[g].text,d[g].text=i.text,t=d[g].vector,a=Math.round(t[0])>0?"left":Math.round(t[0])<0?"right":"center",null==o.margin?t=this._mapVector(t,p,1.1):(s=o.margin,m=t[0]>0?s:-s,u=t[1]>0?s:-s,m=0===t[0]?0:m,u=0===t[1]?0:u,t=this._mapVector(t,p,1)),i.textAlign=a,i.x=t[0]+m,i.y=t[1]+u,r=o.rotate?[o.rotate/180*Math.PI,t[0],t[1]]:[0,0,0],this.shapeList.push(new n({zlevel:this.getZlevelBase(),z:this.getZBase(),style:i,draggable:!1,hoverable:!1,rotation:r})))},getIndicatorText:function(e,t){return this.polar[e]&&this.polar[e].__ecIndicator[t]&&this.polar[e].__ecIndicator[t].text},getDropBox:function(e){var t,i,e=e||0,n=this.polar[e],a=this.getCenter(e),o=n.__ecIndicator,r=o.length,s=[],l=n.type;if("polygon"==l){for(var h=0;r>h;h++)t=o[h].vector,s.push(this._mapVector(t,a,1.2));i=this._getShape(s,"fill","rgba(0,0,0,0)","",1)}else"circle"==l&&(i=this._getCircle("",1,1.2,a,"fill","rgba(0,0,0,0)"));return i},_addArea:function(e,t,i,n,a,o,r){for(var s,l,h,d,c=this.deepQuery(this._queryTarget,"type"),p=0;t>p;p++)l=(t-p)/t,r&&("polygon"==c?(d=this._getPointList(e,l,i),s=this._getShape(d,"stroke","",a,o)):"circle"==c&&(s=this._getCircle(a,o,l,i,"stroke")),this.shapeList.push(s)),n.show&&(h=(t-p-1)/t,this._addSplitArea(e,n,l,h,i,p))},_getCircle:function(e,t,i,n,a,o){var s=this._getRadius();return new r({zlevel:this.getZlevelBase(),z:this.getZBase(),style:{x:n[0],y:n[1],r:s*i,brushType:a,strokeColor:e,lineWidth:t,color:o},hoverable:!1,draggable:!1})},_getRing:function(e,t,i,n){var a=this._getRadius();return new s({zlevel:this.getZlevelBase(),z:this.getZBase(),style:{x:n[0],y:n[1],r:t*a,r0:i*a,color:e,brushType:"fill"},hoverable:!1,draggable:!1})},_getPointList:function(e,t,i){for(var n,a=[],o=e.length,r=0;o>r;r++)n=e[r].vector,a.push(this._mapVector(n,i,t));return a},_getShape:function(e,t,i,n,a){return new o({zlevel:this.getZlevelBase(),z:this.getZBase(),style:{pointList:e,brushType:t,color:i,strokeColor:n,lineWidth:a},hoverable:!1,draggable:!1})},_addSplitArea:function(e,t,i,n,a,o){var r,s,l,h,d,c=e.length,p=t.areaStyle.color,m=[],c=e.length,u=this.deepQuery(this._queryTarget,"type");if("string"==typeof p&&(p=[p]),s=p.length,r=p[o%s],"polygon"==u)for(var g=0;c>g;g++)m=[],l=e[g].vector,h=e[(g+1)%c].vector,m.push(this._mapVector(l,a,i)),m.push(this._mapVector(l,a,n)),m.push(this._mapVector(h,a,n)),m.push(this._mapVector(h,a,i)),d=this._getShape(m,"fill",r,"",1),this.shapeList.push(d);else"circle"==u&&(d=this._getRing(r,i,n,a),this.shapeList.push(d))},_mapVector:function(e,t,i){return[e[0]*i+t[0],e[1]*i+t[1]]},getCenter:function(e){var e=e||0;return this.parseCenter(this.zr,this.polar[e].center)},_addLine:function(e,t,i){for(var n,a,o=e.length,r=i.lineStyle,s=r.color,l=r.width,h=r.type,d=0;o>d;d++)a=e[d].vector,n=this._getLine(t[0],t[1],a[0]+t[0],a[1]+t[1],s,l,h),this.shapeList.push(n)},_getLine:function(e,t,i,n,o,r,s){return new a({zlevel:this.getZlevelBase(),z:this.getZBase(),style:{xStart:e,yStart:t,xEnd:i,yEnd:n,strokeColor:o,lineWidth:r,lineType:s},hoverable:!1})},_adjustIndicatorValue:function(t){for(var i,n,a,o=this.polar[t],r=this.deepQuery(this._queryTarget,"indicator"),s=r.length,l=o.__ecIndicator,h=this._getSeriesData(t),d=o.boundaryGap,c=o.splitNumber,p=o.scale,m=e("../util/smartSteps"),u=0;s>u;u++){if("number"==typeof r[u].max)i=r[u].max,n=r[u].min||0,a={max:i,min:n};else{var g=this._findValue(h,u,c,d);n=g.min,i=g.max}!p&&n>=0&&i>=0&&(n=0),!p&&0>=n&&0>=i&&(i=0);var V=m(n,i,c,a);l[u].value={min:V.min,max:V.max,step:V.step}}},_getSeriesData:function(e){for(var t,i,n,a=[],o=this.component.legend,r=0;r<this.series.length;r++)if(t=this.series[r],t.type==l.CHART_TYPE_RADAR){i=t.data||[];for(var s=0;s<i.length;s++)n=this.deepQuery([i[s],t,this.option],"polarIndex")||0,n!=e||o&&!o.isSelected(i[s].name)||a.push(i[s])}return a},_findValue:function(e,t,i,n){function a(e){(e>o||void 0===o)&&(o=e),(r>e||void 0===r)&&(r=e)}var o,r,s;if(e&&0!==e.length){if(1==e.length&&(r=0),1!=e.length)for(var l=0;l<e.length;l++)a(this.getDataFromOption(e[l].value[t]));else{s=e[0];for(var l=0;l<s.value.length;l++)a(this.getDataFromOption(s.value[l]))}var h=Math.abs(o-r);return r-=Math.abs(h*n[0]),o+=Math.abs(h*n[1]),r===o&&(0===o?o=1:o>0?r=o/i:o/=i),{max:o,min:r}}},getVector:function(e,t,i){e=e||0,t=t||0;var n=this.polar[e].__ecIndicator;if(!(t>=n.length)){var a,o=this.polar[e].__ecIndicator[t],r=this.getCenter(e),s=o.vector,l=o.value.max,h=o.value.min;if("undefined"==typeof i)return r;switch(i){case"min":i=h;break;case"max":i=l;break;case"center":i=(l+h)/2}return a=l!=h?(i-h)/(l-h):.5,this._mapVector(s,r,a)}},isInside:function(e){var t=this.getNearestIndex(e);return t?t.polarIndex:-1},getNearestIndex:function(e){for(var t,i,n,a,o,r,s,l,h,c=0;c<this.polar.length;c++){if(t=this.polar[c],i=this.getCenter(c),e[0]==i[0]&&e[1]==i[1])return{polarIndex:c,valueIndex:0};if(n=this._getRadius(),o=t.startAngle,r=t.indicator,s=r.length,l=2*Math.PI/s,a=d.cartesian2polar(e[0]-i[0],i[1]-e[1]),e[0]-i[0]<0&&(a[1]+=Math.PI),a[1]<0&&(a[1]+=2*Math.PI),h=a[1]-o/180*Math.PI+2*Math.PI,Math.abs(Math.cos(h%(l/2)))*n>a[0])return{polarIndex:c,valueIndex:Math.floor((h+l/2)/l)%s}}},getIndicator:function(e){var e=e||0;return this.polar[e].indicator},refresh:function(e){e&&(this.option=e,this.polar=this.option.polar,this.series=this.option.series),this.clear(),this._buildShape()}},h.inherits(t,i),e("../component").define("polar",t),t}),define("echarts/util/coordinates",["require","zrender/tool/math"],function(e){function t(e,t){return[e*n.sin(t),e*n.cos(t)]}function i(e,t){return[Math.sqrt(e*e+t*t),Math.atan(t/e)]}var n=e("zrender/tool/math");return{polar2cartesian:t,cartesian2polar:i}});