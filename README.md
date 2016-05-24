Flocking/Boids Demo
====================================================

(a Distributed Behavioral Model simulation)
-------------------------------------------

![Image with boids with shape of black arrows and steer vectors on white background.] (https://raw.githubusercontent.com/exigow/flocking-boids/master/screenshots/preview-on-white-background.png)

An implementation of Craig Reynold's Boids program ([source] (http://www.red3d.com/cwr/boids/)) to simulate the flocking behavior of birds in two dimensional space. Each boid steers itself based on rules of __avoidance__, __alignment__, and __coherence__.

All written in **Scala** language.

Optimization
------------

The neighbors detection and vector calculations are build on top of **2d grid spatial partitioning**.