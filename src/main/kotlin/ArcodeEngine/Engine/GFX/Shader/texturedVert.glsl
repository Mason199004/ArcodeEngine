#version 400 core

in vec3 position;
in vec2 texCoords;

out vec2 pass_Texture;

uniform mat4 mvMatrix;
uniform mat4 projMatrix;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = projMatrix * mvMatrix * vec4(position, 1.0);
    pass_Texture = texCoords;
}