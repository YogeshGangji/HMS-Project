import React, { useState } from 'react'
import bgImage from '/bg.jpg'
import { Button, PasswordInput, SegmentedControl, TextInput } from '@mantine/core'
import { IconHeartbeat } from '@tabler/icons-react'
import { useForm } from '@mantine/form';
import { data, Link, useNavigate } from 'react-router-dom';
import { registerUser } from '../Service/UserService';
import { errorNotification, successNotification } from '../Utility/NotificationUtil';

const RegisterPage = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const form = useForm({
        initialValues: {
            role: 'PATIENT',
            name: '',
            email: '',
            password: '',
            confirmPassword: '',

        },

        validate: {
            name: (value) => !value ? "Name is required" : null,
            email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
            password: (value) => (
                !value
                    ? "Password is required"
                    : !/^(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>/?]).{8,}$/.test(value)
                        ? "Password must be at least 8 characters with 1 number and 1 symbol"
                        : null),

            confirmPassword: (value, values) => (!value ? "Confirm password is required" : value !== values.password ? "password don't match" : null),
        },
    });

    const handleSubmit = (values: typeof form.values) => {

        console.log('Submitting:', values);
        setLoading(true);

        registerUser(values).then((data) => {
            console.log(data)
            successNotification("Registered Succesfully");
            navigate("/login");
        }).catch((error) => {
            console.log("axios ", error);
            errorNotification(error.response.data.errorMessage);
        }).finally(() => {
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
                <form onSubmit={form.onSubmit(handleSubmit, (errors) => console.log('Validation errors:', errors))} action="" className='flex flex-col gap-5  [&_input]:!placeholder-neutral-100 [&_.mantine-Input-input]:!border-white focus-within:[&_.antine-Input-input]:!border-pink-400 [&_.mantine-Input-input]:!border [&_input]:!pl-2 [&_svg]:text-white [&_input]:!text-white'>
                    <div className="self-center font-medium text-white text-2xl ">Register</div>

                    <SegmentedControl color='pink' bg="none" className="[&_*]:!text-white border-white" {...form.getInputProps('role')} fullWidth size="md" data={[{ label: 'Patient', value: 'PATIENT' }, { label: 'Doctor', value: 'DOCTOR' }, { label: 'Admin', value: 'ADMIN' }]} />
                    <TextInput {...form.getInputProps('name')} variant='unstyled' size='md' radius="md" placeholder='Name' className='focus-within:[&_input]:!border-pink-400' />
                    <TextInput {...form.getInputProps('email')} variant='unstyled' size='md' radius="md" placeholder='Email' className='focus-within:[&_input]:!border-pink-400' />
                    <PasswordInput {...form.getInputProps('password')} variant='unstyled' size='md' radius="md" placeholder='Password' />

                    <PasswordInput {...form.getInputProps('confirmPassword')} variant='unstyled' size='md' radius="md" placeholder='confirmpassword' />

                    <Button loading={loading} radius="md" size="md" type='submit' color='pink'>Register</Button>

                    <div className='text-neutral-100 text-sm self-center'>Alrady have an account? <Link className='hover:underline' to="/login">Login</Link></div>

                </form>
            </div>
        </div>
    )
}

export default RegisterPage