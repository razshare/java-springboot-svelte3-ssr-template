<script>
import asd from './www/asd.js';
let tmp = asd();
function click(){
    tmp += asd();
}
</script>
<button on:click={click}>
    this is a test {tmp}
</button>