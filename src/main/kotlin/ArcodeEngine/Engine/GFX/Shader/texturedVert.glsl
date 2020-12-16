#version 400 core

in vec3 position;
in vec2 texCoords;

out vec3 color;
out vec2 pass_Texture;

uniform mat4 mvpMatrix;

uniform vec3 inColor;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = mvpMatrix * vec4(position, 1.0);
    color = inColor;
    pass_Texture = texCoords;
}