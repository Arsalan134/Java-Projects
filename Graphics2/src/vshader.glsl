// VERTEX SHADER

// input a 2-dimensional float array
in vec2 vertexPosition;

void main()
{
    gl_Position = vec4(vertexPosition, 0, 1.0);
}