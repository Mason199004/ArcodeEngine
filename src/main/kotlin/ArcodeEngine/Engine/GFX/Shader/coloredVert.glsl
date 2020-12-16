#version 400 core

in vec3 position;

out vec3 color;

uniform mat4 mvpMatrix;
uniform vec3 inColor;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = mvpMatrix * vec4(position, 1.0);
    color = inColor;
}