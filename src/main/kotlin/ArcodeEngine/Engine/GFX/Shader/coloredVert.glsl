#version 300 es

in mediump vec3 position;

out mediump vec3 color;

uniform mat4 mvMatrix;
uniform mat4 projMatrix;
uniform vec3 inColor;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = projMatrix * mvMatrix * vec4(position, 1.0);
    color = inColor;
} 