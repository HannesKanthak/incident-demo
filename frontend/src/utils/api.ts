export async function getText(path: string): Promise<string> {
    const res = await fetch(path)
    if (!res.ok) {
        throw new Error(`GET ${path} failed: ${res.status} ${res.statusText}`)
    }
    return await res.text()
}


export async function get<T>(path: string): Promise<T> {
    const res = await fetch(path)

    if (!res.ok) {
        throw new Error(`GET ${path} failed: ${res.status} ${res.statusText}`)
    }

    return await res.json() as T
}

export async function post<T, B = unknown>(
    path: string,
    body: B
): Promise<T> {
    const res = await fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    })

    if (!res.ok) {
        throw new Error(`POST ${path} failed: ${res.status} ${res.statusText}`)
    }

    return await res.json() as T
}

export async function put<T, B = unknown>(
    path: string,
    body: B
): Promise<T> {
    const res = await fetch(path, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    })

    if (!res.ok) {
        throw new Error(`PUT ${path} failed: ${res.status} ${res.statusText}`)
    }

    return await res.json() as T
}

export async function patch<T, B = unknown>(
    path: string,
    body: B
): Promise<T> {
    const res = await fetch(path, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    })

    if (!res.ok) {
        throw new Error(`PATCH ${path} failed: ${res.status} ${res.statusText}`)
    }

    return await res.json() as T
}
