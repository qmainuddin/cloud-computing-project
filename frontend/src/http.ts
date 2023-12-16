import Axios from 'axios'

const axios = Axios.create({
    baseURL: 'http://localhost:8080',
})

axios.interceptors.response.use(async (response) => {
    if (response.status >= 400 && response.status < 500) {
        localStorage.setItem('token', '')
        window.location.href = '/'
    }
    return response
}, async (error) => {
    if (error.response.status === 401 || error.response.status === 403) {
        alert('Unauthorized')
        localStorage.setItem('token', '')
        window.location.href = '/'
        return
    }
    // Promise.reject(error)
    console.error(error)
})

export const login = async (data: any) => {
    const response = await axios.post('/api/auth/login', data)
    return response?.data?.accessToken
}

export const signUp = async (email: string, password: string, licenseNumber: string) => {
    const response = await axios.post('/api/auth/register', {
        email, password, licenseNumber
    })
    return response.data.accessToken
}

export const getProducts = async (token: string, page: number) => {
    const q = `?page=${page || 0}`
    const response = await axios.get(`/api/products${q}`, { headers: { Authorization: `Bearer ${token}` } })
    return response.data.content
}

export const getProduct = async (token: string, id?: string) => {
    const response = await axios.get(`/api/products/${id}`, { headers: { Authorization: `Bearer ${token}` } })
    return response.data
}

export const createProduct = async (token: string, data: any) => {
    const response = await axios.post(`/api/products`, {
        ...data,
    }, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const getCategories = async (token: string) => {
    try {
        const response = await axios.get('/api/categories', {
            headers: { Authorization: `Bearer ${token}` }
        })
        return response.data
    } catch (ex) {
        return []
    }
}

export const saveProduct = async (token: string, id: string, data: any) => {
    const response = await axios.put('/api/products/' + id, data, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const publishProduct = async (token: string, id: string, data: any) => {
    const response = await axios.post('/api/products/publish/' + id, data, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const getBid = async (token: string, id: string) => {
    const response = await axios.get('/api/bids/' + id, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const getPublishedBids = async (token: string, page: number) => {
    const q = `?page=${page || 0}`
    const response = await axios.get(`/api/bids${q}`, { headers: { Authorization: `Bearer ${token}` } })
    return response.data.content
}

export const getMyBids = async (token: string, page: number) => {
    const response = await axios.get(`/api/bids/my?page=${page || 0}`, { headers: { Authorization: `Bearer ${token}` } })
    return response.data.content
}

export const doBid = async (token: string, bidId: string, data: any) => {
    try {
        const response = await axios.post(`/api/auctions/${bidId}/bid`, data, {
            headers: { Authorization: `Bearer ${token}` }
        })
        return response.data
    } catch (ex: any) {
        console.error(ex.response)
        alert(ex?.response || '')
        throw ex
        // return ex?.response?.data || ''
    }
}

export const getMe = async (token: string) => {
    const response = await axios.get('/api/users/me', {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const updateUser = async (token: string, data: any) => {
    const response = await axios.put('/api/users/me', data, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const removeProduct = async (token: string, id: string) => {
    const response = await axios.delete('/api/products/' + id, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const updateBid = async (token: string, id: string, data: any) => {
    const response = await axios.put('/api/bids/' + id, data, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const publishTheBid = async (token: string, id: string) => {
    const response = await axios.patch('/api/bids/publish/' + id, {}, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const getAuctions = async (token: string, page: number, year?: number | null) => {
    let q = `?page=${page || 0}`
    q += (year ? `&year=${year}` : '')
    const response = await axios.get(`/api/auctions/mine${q}`, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const getAuctionsByProposal = async (token: string, id: string) => {
    const response = await axios.get(`/api/auctions/proposal/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}

export const increaseBid = async (token: string, id: string, data: any) => {
    const response = await axios.post(`/api/auctions/${id}/increase`, data, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response?.data
}

export const getClosedBids = async (token: string) => {
    const response = await axios.get(`/api/bids/closed`, {
        headers: { Authorization: `Bearer ${token}` }
    })
    return response.data
}