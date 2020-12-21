#version 400 core

in vec3 position;
in vec2 texCoords;

out vec2 pass_Texture;

uniform mat4 mvpMatrix;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = mvpMatrix * vec4(position, 1.0);
    pass_Texture = texCoords;
}