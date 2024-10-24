import { createApp } from 'vue';
import App from './Top.vue';
import vuetify from './assets/js/vuetify';
import FontAwesomeIcon from './assets/js/fortawesome';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import './assets/style/common.css';

// VTooltip
import { Tooltip } from 'floating-vue';
import 'floating-vue/dist/style.css';

const app = createApp(App);
app.use(vuetify);
app.component('VTooltip', Tooltip);
app.component('FontAwesomeIcon', FontAwesomeIcon);
app.mount('#app');
