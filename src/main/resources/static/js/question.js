(function(){"use strict";var e={7497:function(e,t,o){var n=o(5130),a=o(6768);const s=(0,a.Lk)("header",{class:"pt-5"},[(0,a.Lk)("h3",{class:"display-6"},[(0,a.Lk)("span",{class:"markerGray"}," Q&A ")])],-1),r={class:"mx-4 mb-5"};function i(e,t,o,n,i,l){const c=(0,a.g2)("NavBar"),d=(0,a.g2)("PanelPostQuestion"),u=(0,a.g2)("PanelSearchCond"),g=(0,a.g2)("TableKnowledge"),p=(0,a.g2)("Footer"),b=(0,a.g2)("v-main"),f=(0,a.g2)("v-layout"),h=(0,a.gN)("resize");return(0,a.uX)(),(0,a.Wv)(f,null,{default:(0,a.k6)((()=>[(0,a.bF)(c),(0,a.bF)(b,null,{default:(0,a.k6)((()=>[(0,a.bo)((0,a.Lk)("span",null,null,512),[[h,l.onResize]]),s,(0,a.Lk)("div",r,[(0,a.bF)(d,{categories:i.categories,subcategories:i.subcategories,knowledges:i.knowledges,endpoint:i.endpoint,csrfToken:e.csrfToken,windowSize:i.windowSize,ref:"cmpPost",onCallParentPost:l.post,class:"mx-auto px-1 py-3"},null,8,["categories","subcategories","knowledges","endpoint","csrfToken","windowSize","onCallParentPost"]),(0,a.bF)(u,{categories:i.categories,subcategories:i.subcategories,endpoint:i.endpoint,windowSize:i.windowSize,onCallParentSearch:l.search,class:"mx-auto px-1 pt-3 pb-4"},null,8,["categories","subcategories","endpoint","windowSize","onCallParentSearch"]),(0,a.bF)(g,{headers:i.headers,endpoint:i.endpoint,searchCond:i.searchCond,ref:"cmpKnowledge",onCallParentAnswer:l.answer},null,8,["headers","endpoint","searchCond","onCallParentAnswer"])]),(0,a.bF)(p)])),_:1})])),_:1})}var l=o(6587),c=o.n(l),d=o(2664),u=o(9122),g=o.n(u),p=o(1565);const b={key:0},f=(0,a.Lk)("label",{class:"form-label"},"カテゴリー",-1),h=(0,a.Lk)("label",{class:"form-label"},"サブカテゴリー",-1),w={key:1},y=(0,a.Lk)("label",{class:"form-label"},"カテゴリー",-1),m=(0,a.Lk)("label",{class:"form-label"},"サブカテゴリー",-1),k=(0,a.Lk)("label",{class:"form-label"},"質問",-1);function C(e,t,o,s,r,i){const l=(0,a.g2)("v-expansion-panel-title"),c=(0,a.g2)("SelectCategory"),d=(0,a.g2)("v-col"),u=(0,a.g2)("SelectSubcategory"),g=(0,a.g2)("v-row"),p=(0,a.g2)("v-container"),C=(0,a.g2)("ButtonCategoryEdit"),v=(0,a.g2)("v-btn"),S=(0,a.g2)("v-expansion-panel-text"),F=(0,a.g2)("v-expansion-panel"),x=(0,a.g2)("v-expansion-panels");return(0,a.uX)(),(0,a.Wv)(x,{modelValue:r.activePanel,"onUpdate:modelValue":t[8]||(t[8]=e=>r.activePanel=e),style:{"max-width":"900px"}},{default:(0,a.k6)((()=>[(0,a.bF)(F,null,{default:(0,a.k6)((()=>[(0,a.bF)(l,{style:{"background-color":"rgb(223, 231, 249)"}},{default:(0,a.k6)((()=>[(0,a.eW)(" 質問投稿 ")])),_:1}),(0,a.bF)(S,null,{default:(0,a.k6)((()=>[(0,a.bF)(p,{class:"px-1 py-2"},{default:(0,a.k6)((()=>[o.windowSize.x>=600?((0,a.uX)(),(0,a.CE)("span",b,[(0,a.bF)(g,null,{default:(0,a.k6)((()=>[(0,a.bF)(d,{cols:"6",class:"pb-1 text-left"},{default:(0,a.k6)((()=>[f,(0,a.bF)(c,{modelValue:r.postContent.categoryId,"onUpdate:modelValue":t[0]||(t[0]=e=>r.postContent.categoryId=e),categories:o.categories,addAble:!1,models:r.postContent,onChange:t[1]||(t[1]=e=>{r.postContent.subcategoryId=""}),ref:"cmpSelectCategory"},null,8,["modelValue","categories","models"])])),_:1}),(0,a.bF)(d,{cols:"6",class:"pb-1 text-left"},{default:(0,a.k6)((()=>[h,(0,a.bF)(u,{modelValue:r.postContent.subcategoryId,"onUpdate:modelValue":t[2]||(t[2]=e=>r.postContent.subcategoryId=e),subcategories:o.subcategories,addAble:!1,models:r.postContent,ref:"cmpSelectSubcategory"},null,8,["modelValue","subcategories","models"])])),_:1})])),_:1})])):((0,a.uX)(),(0,a.CE)("span",w,[(0,a.bF)(g,null,{default:(0,a.k6)((()=>[(0,a.bF)(d,{class:"pb-1 text-left"},{default:(0,a.k6)((()=>[y,(0,a.bF)(c,{modelValue:r.postContent.categoryId,"onUpdate:modelValue":t[3]||(t[3]=e=>r.postContent.categoryId=e),categories:o.categories,addAble:!1,models:r.postContent,onChange:t[4]||(t[4]=e=>{r.postContent.subcategoryId=""}),ref:"cmpSelectCategory"},null,8,["modelValue","categories","models"])])),_:1})])),_:1}),(0,a.bF)(g,{class:"mt-1"},{default:(0,a.k6)((()=>[(0,a.bF)(d,{class:"pb-1 text-left"},{default:(0,a.k6)((()=>[m,(0,a.bF)(u,{modelValue:r.postContent.subcategoryId,"onUpdate:modelValue":t[5]||(t[5]=e=>r.postContent.subcategoryId=e),subcategories:o.subcategories,addAble:!1,models:r.postContent,ref:"cmpSelectSubcategory"},null,8,["modelValue","subcategories","models"])])),_:1})])),_:1})]))])),_:1}),(0,a.bF)(p,{class:"px-1 py-2"},{default:(0,a.k6)((()=>[(0,a.bF)(g,null,{default:(0,a.k6)((()=>[(0,a.bF)(d,{class:"pb-1 text-left"},{default:(0,a.k6)((()=>[k,(0,a.bo)((0,a.Lk)("textarea",{"onUpdate:modelValue":t[6]||(t[6]=e=>r.postContent.question=e),class:"form-control textarea-enabled"},null,512),[[n.Jo,r.postContent.question]])])),_:1})])),_:1})])),_:1}),(0,a.bF)(p,{class:"px-1 pt-0 pb-3 mt-3"},{default:(0,a.k6)((()=>[(0,a.bF)(g,null,{default:(0,a.k6)((()=>[(0,a.bF)(d,{class:"text-right"},{default:(0,a.k6)((()=>[(0,a.bF)(C,{categories:r.editingCategories,subcategories:r.editingSubcategories,knowledgesSaved:o.knowledges,editingknowledges:o.knowledges,endpoint:o.endpoint,csrfToken:o.csrfToken,saveBtnName:"投稿",onCallParentSetCategory:i.setCategory},null,8,["categories","subcategories","knowledgesSaved","editingknowledges","endpoint","csrfToken","onCallParentSetCategory"]),(0,a.bF)(v,{text:"投稿",onClick:t[7]||(t[7]=e=>i.post()),variant:"tonal",color:"primary"})])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})])),_:1},8,["modelValue"])}var v=o(7965),S=o(4463),F=o(5309),x=o(9832),A={name:"cmp-panel-post-question",components:{ButtonCategoryEdit:S.A,SelectCategory:F.A,SelectSubcategory:x.A},props:{categories:[],subcategories:[],knowledges:[],endpoint:String,csrfToken:String,windowSize:{}},data(){return{activePanel:[],postContent:{id:(0,v.A)(),categoryId:"",subcategoryId:"",question:""},editingCategories:[],editingSubcategories:[]}},methods:{setCategory(e,t){this.editingCategories=e,this.editingSubcategories=t},post(){this.$emit("call-parent-post",this.postContent,this.editingCategories,this.editingSubcategories)}},watch:{}},T=o(1241);const _=(0,T.A)(A,[["render",C]]);var P=_,O=o(3298),V=o(3696),I=o(9936),z={name:"App",components:{NavBar:p.A,PanelPostQuestion:P,PanelSearchCond:O.A,TableKnowledge:V.A,Footer:I.A},data(){return{windowSize:{x:0,y:0},headers:[{key:"categoryId",title:"カテゴリー",align:"center",sortable:!1,width:"10%"},{key:"subcategoryId",title:"サブカテゴリー",align:"center",sortable:!1,width:"10%"},{key:"question",title:"質問",align:"center",sortable:!1,width:"20%"},{key:"answer",title:"回答",align:"center",sortable:!1,width:"20%"},{key:"questionDate",title:"質問日時",align:"center",sortable:!0,width:"10%"},{key:"questionUserName",title:"質問者",align:"center",sortable:!1,width:"10%"},{key:"answerDate",title:"回答日時",align:"center",sortable:!0,width:"10%"},{key:"answerUserName",title:"回答者",align:"center",sortable:!1,width:"10%"}],categories:[],subcategories:[],knowledges:[],endpoint:"question",searchCond:{}}},methods:{async fetchCsrfToken(){try{const e=await d.A.get("/csrf-token");this.csrfToken=e.data.csrfToken}catch(e){console.error("CSRFトークンの取得に失敗しました:",e)}},onResize(){this.windowSize={x:window.innerWidth,y:window.innerHeight}},searchCategoryList(){d.A.get(`${window.location.origin}/`+this.endpoint+"/searchCategory").then((e=>{this.categories=e.data.categories,this.subcategories=e.data.subcategories,this.$refs.cmpPost.setCategory(this.categories,this.subcategories),this.$refs.cmpKnowledge.setCategory(this.categories,this.subcategories)})).catch((e=>{console.error("初期設定エラー:",e)}))},async post(e,t,o){try{const n=await d.A.post("/"+this.endpoint+"/question",g().stringify({categories:JSON.stringify(t),subcategories:JSON.stringify(o),postContent:JSON.stringify(e)}),{headers:{"Content-Type":"application/x-www-form-urlencoded","X-CSRF-TOKEN":this.csrfToken},withCredentials:!0});c()("#searchButton").trigger("click"),console.log("Data submitted:",n.data),alert("質問を投稿しました。")}catch(n){console.error("Error submitting data:",n),alert("質問の投稿が失敗しました。再試行してください。")}},async search(e){try{console.log(e);const t=await d.A.post("/"+this.endpoint+"/searchKnowledge",g().stringify({searchCondition:JSON.stringify(e)}),{headers:{"Content-Type":"application/x-www-form-urlencoded","X-CSRF-TOKEN":this.csrfToken},withCredentials:!0});console.log(t.data),this.knowledges=t.data.knowledges,this.searchCond=e,this.$refs.cmpKnowledge.setKnowledge(this.knowledges)}catch(t){console.error("Error submitting data:",t),alert("検索が失敗しました。再試行してください。")}},async answer(e){try{const t=await d.A.post("/"+this.endpoint+"/answer",g().stringify({postContent:JSON.stringify(e)}),{headers:{"Content-Type":"application/x-www-form-urlencoded","X-CSRF-TOKEN":this.csrfToken},withCredentials:!0});c()("#searchButton").trigger("click"),console.log("Data submitted:",t.data),alert("回答しました。")}catch(t){console.error("Error submitting data:",t),alert("回答が失敗しました。再試行してください。")}}},beforeCreate(){},created(){console.log("createdが呼ばれました"),this.searchCategoryList()},async mounted(){await this.fetchCsrfToken(),this.onResize()}};const N=(0,T.A)(z,[["render",i]]);var L=N,E=o(395),K=o(1169),j=(o(9313),o(985)),q=o(9574);const U=(0,n.Ef)(L);U.use(E.A),U.component("VTooltip",j.m_),U.component("FontAwesomeIcon",K.A),U.component("VueDatePicker",q.A),U.mount("#app")}},t={};function o(n){var a=t[n];if(void 0!==a)return a.exports;var s=t[n]={exports:{}};return e[n].call(s.exports,s,s.exports,o),s.exports}o.m=e,function(){var e=[];o.O=function(t,n,a,s){if(!n){var r=1/0;for(d=0;d<e.length;d++){n=e[d][0],a=e[d][1],s=e[d][2];for(var i=!0,l=0;l<n.length;l++)(!1&s||r>=s)&&Object.keys(o.O).every((function(e){return o.O[e](n[l])}))?n.splice(l--,1):(i=!1,s<r&&(r=s));if(i){e.splice(d--,1);var c=a();void 0!==c&&(t=c)}}return t}s=s||0;for(var d=e.length;d>0&&e[d-1][2]>s;d--)e[d]=e[d-1];e[d]=[n,a,s]}}(),function(){o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,{a:t}),t}}(),function(){o.d=function(e,t){for(var n in t)o.o(t,n)&&!o.o(e,n)&&Object.defineProperty(e,n,{enumerable:!0,get:t[n]})}}(),function(){o.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}}(),function(){o.j=383}(),function(){var e={383:0};o.O.j=function(t){return 0===e[t]};var t=function(t,n){var a,s,r=n[0],i=n[1],l=n[2],c=0;if(r.some((function(t){return 0!==e[t]}))){for(a in i)o.o(i,a)&&(o.m[a]=i[a]);if(l)var d=l(o)}for(t&&t(n);c<r.length;c++)s=r[c],o.o(e,s)&&e[s]&&e[s][0](),e[s]=0;return o.O(d)},n=self["webpackChunkvue_app"]=self["webpackChunkvue_app"]||[];n.forEach(t.bind(null,0)),n.push=t.bind(null,n.push.bind(n))}();var n=o.O(void 0,[504,996],(function(){return o(7497)}));n=o.O(n)})();
//# sourceMappingURL=question.js.map