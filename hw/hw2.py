import numpy as np
import matplotlib.pyplot as plt

iteration_space = []
for i in range(1, 30):
    for j in range(i + 2, 40 - i):
        iteration_space.append((i, j))

# Plot the iteration space
plt.figure(figsize=(10, 10))
plt.scatter([i[0] for i in iteration_space], [j[1] for j in iteration_space])
plt.xlabel("$i$")
plt.ylabel("$j$")
plt.title("Iteration space for a")
plt.savefig("figures/iteration_space_a.png")

iteration_space = []
for i in range(10, 1001):
    for j in range(i, i + 10):
        iteration_space.append((i, j))

# Plot the iteration space
plt.figure(figsize=(10, 10))
plt.scatter([i[0] for i in iteration_space], [j[1] for j in iteration_space])
plt.xlabel("$i$")
plt.ylabel("$j$")
plt.title("Iteration space for b")
plt.savefig("figures/iteration_space_b.png")
