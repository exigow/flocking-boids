Flocking/Boids Demo
====================================================

(a Distributed Behavioral Model simulation)
-------------------------------------------

![Image with boids with shape of black arrows and steer vectors on white background.]
(https://raw.githubusercontent.com/exigow/flocking-boids/master/screenshots/preview-on-white-background.png)

An implementation of Craig Reynold's Boids program ([papers] (http://www.red3d.com/cwr/boids/)) to simulate the flocking 
behavior of birds in two dimensional space. Each boid steers itself based on rules of __avoidance__, __alignment__, and 
__coherence__.

Fully written in **Scala** language.

Rendered with libGDX
------------
This library was used to show window and render simulation. This can be easily replaced with some other framework.

Optimization
------------

The neighbors detection and vector calculations are build on top of **2d grid spatial partitioning** algorithm. 
Everything works very **fast**.