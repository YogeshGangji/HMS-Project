import React, { useState } from 'react'
import bgImage from '/bg.jpg'
import { Button, PasswordInput, TextInput } from '@mantine/core'
import { IconHeartbeat } from '@tabler/icons-react'
import { useForm } from '@mantine/form';
import { Link, useNavigate } from 'react-router-dom';
import { loginUser } from '../Service/UserService';
import { errorNotification, successNotification } from '../Utility/NotificationUtil';
import { useDispatch } from 'react-redux';
import { setJwt } from '../Slices/JwtSlice';
import { jwtDecode } from "jwt-decode";
import { setUser } from '../Slices/UserSlices';

const LoginPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false); 
    const form = useForm({
        initialValues: {
            email: '',
            password: '',
        },

        validate: {
            email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
            password: (value) => (!value ? "password is required" : null),
        },
    });

    const handleSubmit = (values: typeof form.values) => {
        // console.log(values);
        setLoading(true);
        loginUser(values).then((_data) => {

            dispatch(setJwt(_data))
            dispatch(setUser(jwtDecode(_data)));
            successNotification("Logged in Successfully");
        }).catch((error) =>{

            console.log(error);
            errorNotification(error?.response?.data?.errorMessage);
        }).finally(() =>{
            setLoading(false);
        })
    };

    return (
        <div style={{ background: 'url("/bg.jpg")' }} className="h-screen w-screen !bg-cover !bg-center !bg-no-repeat flex flex-col items-center justify-center">

            <div className='py-3 text-pink-600 flex gap-1 items-center'>
                <IconHeartbeat size={45} stroke={2.5} />
                <span className='font-heading font-semibold text-4xl'>Pulse</span>
            </div>
            <div className='w-[450px] backdrop-blur-md p-10 py-8 rounded-lg'>
                <form onSubmit={form.onSubmit(handleSubmit)} action="" className='flex flex-col gap-5  [&_input]:!placeholder-neutral-100 [&_.mantine-Input-input]:!border-white focus-within:[&_.mantine-Input-input]:!border-pink-400 [&_.mantine-Input-input]:!border [&_input]:!pl-2 [&_svg]:text-white [&_input]:!text-white'>
                    <div className="self-center font-medium text-white text-2xl ">Login</div>

                    <TextInput {...form.getInputProps('email')} variant='unstyled' size='md' radius="md" placeholder='Email' />
                    <PasswordInput {...form.getInputProps('password')} variant='unstyled' size='md' radius="md" placeholder='Password' />

                    <Button loading={loading} radius="md" size="md" type='submit' color='pink'>Login</Button>

                    <div className='text-neutral-100 text-sm self-center'>Don't have an account? <Link className='hover:underline' to="/register">Register</Link></div>

                </form>
            </div>
        </div>
    )
}

export default LoginPage