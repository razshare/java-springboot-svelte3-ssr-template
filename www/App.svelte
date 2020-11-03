<script>
    import Test from './www/Test.svelte';
    import test from './www/test.js';
</script>
<b on:click={()=>test()}>
    hello world
</b>
<Test/>