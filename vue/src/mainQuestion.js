import { createApp } from 'vue';
import App from './Question.vue';
import vuetify from './assets/js/vuetify';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import './assets/style/common.css';

// VTooltip
import { Tooltip } from 'floating-vue';
import 'floating-vue/dist/style.css';

// fontawesome
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
	faCube,
	faComments,
	faRightFromBracket,
	faCirclePlus,
	faPenToSquare,
	faTrash,
	faFloppyDisk,
} from '@fortawesome/free-solid-svg-icons';
library.add(
	faCube,
	faComments,
	faRightFromBracket,
	faCirclePlus,
	faPenToSquare,
	faTrash,
	faFloppyDisk,
);

// VueDatePicker
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';


const app = createApp(App);
app.use(vuetify);
app.component('VTooltip', Tooltip);
app.component('FontAwesomeIcon', FontAwesomeIcon);
app.component('VueDatePicker', VueDatePicker);
app.mount('#app');
