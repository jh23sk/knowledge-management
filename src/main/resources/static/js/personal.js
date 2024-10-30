(function(){"use strict";var e={3908:function(e,t,n){var o=n(5130),r=n(6768);const i=(0,r.Lk)("header",{class:"pt-5"},[(0,r.Lk)("h3",{class:"display-6"},[(0,r.Lk)("span",{class:"markerGray"}," 個人ナレッジ ")])],-1),a={class:"mx-4 mb-5"};function s(e,t,n,o,s,c){const l=(0,r.g2)("NavBar"),d=(0,r.g2)("PanelSearchCond"),u=(0,r.g2)("TableKnowledge"),f=(0,r.g2)("Footer"),g=(0,r.g2)("v-main"),h=(0,r.g2)("v-layout"),p=(0,r.gN)("resize");return(0,r.uX)(),(0,r.Wv)(h,null,{default:(0,r.k6)((()=>[(0,r.bF)(l),(0,r.bF)(g,null,{default:(0,r.k6)((()=>[(0,r.bo)((0,r.Lk)("span",null,null,512),[[p,c.onResize]]),i,(0,r.Lk)("div",a,[(0,r.bF)(d,{categories:s.categories,subcategories:s.subcategories,endpoint:s.endpoint,windowSize:s.windowSize,onCallParentSearch:c.search,class:"mx-auto px-1 pt-3 pb-4"},null,8,["categories","subcategories","endpoint","windowSize","onCallParentSearch"]),(0,r.bF)(u,{headers:s.headers,endpoint:s.endpoint,csrfToken:s.csrfToken,ref:"cmpKnowledge",onCallParentSave:c.save},null,8,["headers","endpoint","csrfToken","onCallParentSave"])]),(0,r.bF)(f)])),_:1})])),_:1})}var c=n(2664),l=n(9122),d=n.n(l),u=n(1565),f=n(3298),g=n(4640),h=n(9936),p={name:"App",components:{NavBar:u.A,PanelSearchCond:f.A,TableKnowledge:g.A,Footer:h.A},data(){return{windowSize:{x:0,y:0},csrfToken:"",headers:[{key:"categoryId",title:"カテゴリー",align:"center",sortable:!1,width:"15%"},{key:"subcategoryId",title:"サブカテゴリー",align:"center",sortable:!1,width:"15%"},{key:"title",title:"タイトル",align:"center",sortable:!1,width:"25%"},{key:"content",title:"内容",align:"center",sortable:!1,width:"45%"}],categories:[],subcategories:[],knowledges:[],endpoint:"personal",searchCond:{}}},methods:{async fetchCsrfToken(){try{const e=await c.A.get("/csrf-token");this.csrfToken=e.data.csrfToken}catch(e){console.error("CSRFトークンの取得に失敗しました:",e)}},onResize(){this.windowSize={x:window.innerWidth,y:window.innerHeight}},searchCategoryList(){c.A.get("/"+this.endpoint+"/searchCategory").then((e=>{this.categories=e.data.categories,this.subcategories=e.data.subcategories,console.log("↓searchCategoryのレスポンス"),console.log(e.data),this.$refs.cmpKnowledge.setCategory(this.categories,this.subcategories)})).catch((e=>{console.error("初期設定エラー:",e)}))},async search(e){try{const t=await c.A.post("/"+this.endpoint+"/searchKnowledge",d().stringify({searchCondition:JSON.stringify(e)}),{headers:{"Content-Type":"application/x-www-form-urlencoded","X-CSRF-TOKEN":this.csrfToken},withCredentials:!0});this.knowledges=t.data.knowledges,this.searchCond=e,this.$refs.cmpKnowledge.setKnowledge(this.knowledges)}catch(t){console.error("Error submitting data:",t),alert("検索が失敗しました。再試行してください。")}},async save(e,t,n,o){if(o){if(!confirm("選択した行を削除し、保存してよろしいですか？"))return!0;n=this.$refs.cmpKnowledge.removeSelectedKnowledges()}try{const o=this.searchCond,r=await c.A.post("/"+this.endpoint+"/save",d().stringify({categories:JSON.stringify(e),subcategories:JSON.stringify(t),knowledges:JSON.stringify(n),searchCondition:JSON.stringify(o)}),{headers:{"Content-Type":"application/x-www-form-urlencoded","X-CSRF-TOKEN":this.csrfToken},withCredentials:!0});console.log("Data submitted:",r.data),alert("保存しました。")}catch(r){console.error("Error submitting data:",r),alert("保存が失敗しました。再試行してください。")}}},beforeCreate(){},created(){console.log("personalのcreated"),this.searchCategoryList()},async mounted(){await this.fetchCsrfToken(),this.onResize()}},w=n(1241);const y=(0,w.A)(p,[["render",s]]);var b=y,v=n(395),k=n(1169),m=(n(9313),n(985)),C=n(9574),S=n(4014);const T=(0,o.Ef)(b);T.use(v.A),T.component("VTooltip",m.m_),T.component("FontAwesomeIcon",k.A),T.component("VueDatePicker",C.A),T.component("VueMultiselect",S.A),T.mount("#app")}},t={};function n(o){var r=t[o];if(void 0!==r)return r.exports;var i=t[o]={exports:{}};return e[o].call(i.exports,i,i.exports,n),i.exports}n.m=e,function(){var e=[];n.O=function(t,o,r,i){if(!o){var a=1/0;for(d=0;d<e.length;d++){o=e[d][0],r=e[d][1],i=e[d][2];for(var s=!0,c=0;c<o.length;c++)(!1&i||a>=i)&&Object.keys(n.O).every((function(e){return n.O[e](o[c])}))?o.splice(c--,1):(s=!1,i<a&&(a=i));if(s){e.splice(d--,1);var l=r();void 0!==l&&(t=l)}}return t}i=i||0;for(var d=e.length;d>0&&e[d-1][2]>i;d--)e[d]=e[d-1];e[d]=[o,r,i]}}(),function(){n.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return n.d(t,{a:t}),t}}(),function(){n.d=function(e,t){for(var o in t)n.o(t,o)&&!n.o(e,o)&&Object.defineProperty(e,o,{enumerable:!0,get:t[o]})}}(),function(){n.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){n.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){n.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}}(),function(){n.j=879}(),function(){var e={879:0};n.O.j=function(t){return 0===e[t]};var t=function(t,o){var r,i,a=o[0],s=o[1],c=o[2],l=0;if(a.some((function(t){return 0!==e[t]}))){for(r in s)n.o(s,r)&&(n.m[r]=s[r]);if(c)var d=c(n)}for(t&&t(o);l<a.length;l++)i=a[l],n.o(e,i)&&e[i]&&e[i][0](),e[i]=0;return n.O(d)},o=self["webpackChunkvue_app"]=self["webpackChunkvue_app"]||[];o.forEach(t.bind(null,0)),o.push=t.bind(null,o.push.bind(o))}();var o=n.O(void 0,[504,996],(function(){return n(3908)}));o=n.O(o)})();
//# sourceMappingURL=personal.js.map