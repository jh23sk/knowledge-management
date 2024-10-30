import { createApp } from 'vue';
import App from './Personal.vue';
import vuetify from './assets/js/vuetify';
import FontAwesomeIcon from './assets/js/fortawesome';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import './assets/style/common.css';

// VueDatePicker
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

const app = createApp(App);
app.use(vuetify);
app.component('FontAwesomeIcon', FontAwesomeIcon);
app.component('VueDatePicker', VueDatePicker);
app.mount('#app');
