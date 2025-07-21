document.addEventListener('DOMContentLoaded', () => {
    setTimeout(() => {
        document.getElementById('mensaje-bienvenida').classList.add('visible');
    }, 1500);

    setTimeout(() => {
        const grieta = document.getElementById('grieta');
        grieta.classList.remove('oculto');
        gsap.fromTo(grieta, { scale: 0}, { scale: 1, duration: 1.2, ease: "back.out(1.7)"});
    }, 3000);

    // Prismáticos aparecen con movimiento de escaneo.
        setTimeout(() => {
            const prism = document.getElementById('prismaticos')
            prism.classList.remove('oculto');
            prism.classList.add('visible');
            gsap.to("#prismaticos", {
                rotateZ: 10,
                y: -20,
                repeat: 5,
                yoyo: true,
                duration: 0.5,
                ease: "power1.inOut"
            });
        }, 4300);

    /*function crearBultos() {
     *   for (let i = 0; i < 6; i++) {
     *       const bulto = document.createElement('div');
     *       bulto.className = 'bulto';
     *       bulto.style.top = `${Math.random() * 70 + 10}%`;
     *       bulto.style.left = `${Math.random() * 80 + 5}%`;
     *       document.body.appendChild(bulto);
     *
     *       setTimeout(() => {
     *           bulto.remove();
     *       }, 2000);
     *   }
     * }
     */


    // Bocadillo final.
    setTimeout(() => {
    document.getElementById('bocadillo').classList.add('visible');
    }, 6500);
});