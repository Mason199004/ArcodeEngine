#version 300 es 

in mediump vec3 position;
in mediump vec2 texCoords;

out mediump vec2 pass_Texture;

uniform mat4 mvMatrix;
uniform mat4 projMatrix;

void main(void) {
    // proj * view * model(transformation)
    gl_Position = projMatrix * mvMatrix * vec4(position, 1.0);
    pass_Texture = texCoords;
}
